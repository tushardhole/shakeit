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
