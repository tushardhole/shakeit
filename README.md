# Shakeit
An android library to handle shake events. Now don't worry about boiler plate code to handle shake events.

Just implement ShakeListener.OnShake() in your activity and handle the shake events.
The library also takes cares of very close shake events.

The library will register and unregister shake detection with respect to onResume() and onPause()
of the activity which implements ShakeListener.OnShake()

In case your requirement is to handle shake events in non activty class,
that is also possible.

# Installation
```
implementation 'com.github.tushardhole:shakeit:0.0.2'
```

# Usages
In your Application Class onCreate() add below line,

**In Java**
```java
ShakeIt.INSTANCE.init(this);
```

**In Kotlin**
```Kotlin
ShakeIt.init(this)
```

The activity where you want to handle shake event, just make that Activity implement ShakeListener.

```Kotlin
class MainActivity : AppCompatActivity(), ShakeListener {
    override fun onShake() {
        makeText(this@MainActivity, "Shake Detected!!", LENGTH_LONG).show()
    }
}
```

If the is requirement to handle shake events from non activity class, then just register your shake listener with ShakeDetector.

```Kotlin
        ShakeDetector.registerForShakeEvent(object : ShakeListener {
            override fun onShake() {
                makeText(this@MainActivity, "Ok", LENGTH_LONG).show()
            }
        })
```

Also make sure to unregister the same listener,
```Kotlin
ShakeDetector.unRegisterForShakeEvent(mShakeListener)
```

# Contributing
 Please use the pull request procedure for contributing
 
# License

    Copyright 2018 Tushar Dhole

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
