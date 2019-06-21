# VmPusher module and "QuestApp" demo

## Let me show the simple demo ```"QuestApp"``` with usage ```Kotlin```, ```MVVM```, ```Databindg```, ```RxJava2``` together  with my personal created module ```VMPusher``` for the one thing! 
## More easly ```ViewModel``` and ```View``` interaction like ```MVP``` style!

# Intro 

To send an execute action from ```ViewModel``` to ```View``` (still using ```Google Live Data``` under the hood ) with ```VmPusher``` will looks like:

in ```ViewModel```:

```kotlin
  val viewCommander: ViewCommander = ViewCommander()
  startClockMainView() pushBy viewCommander
```
in ```Activity```:

```kotlin

 class MainActivity : BaseActivity<MainActivityBinding>(), MainViewModel.Companion.MainView{
   override fun startClock() {
    //...
   }
 }
 
```

And that's all!!

In under the hood you still use ```LiveData```, but now no need to write ```observe``` method, create ```LiveData```, create ```MutableLiveData```, code to add ```Life Circle Owner``` to observe method and other boiler plate!
```VmPusher``` do it self! Amazing!

# Details

I have create module the [```vmtaskpusher```](https://github.com/SergeyBurlaka/QuestApp-MVVM/tree/feature/improve_code_gen/vmtaskpusher) 
for more easly calling method on view. (Usage LiveData and Kotlin codogeneration under the hood)
It can be usefully in some cases (For navigation, ask view ask to execute action etc)

# Usage sample 

 First download the source and add  1 main ([```vmtaskpusher```](https://github.com/SergeyBurlaka/QuestApp-MVVM/tree/feature/improve_code_gen/vmtaskpusher)) and 2 supporting modules ([```vmpusherannotation```](https://github.com/SergeyBurlaka/VmPusher/tree/feature/improve_code_gen/vmpusherannotation) and [vmpusherprocessor](https://github.com/SergeyBurlaka/VmPusher/tree/feature/improve_code_gen/vmpusherprocessor)) to your project ass libraries. Than in gradle you need write modules dependency:

```groovy
//vmpusher
dependencies {
    implementation project(':vmtaskpusher')
    compileOnly project(path: ':vmpusherannotation')
    kapt project(':vmpusherprocessor')
}
```
# So now you ready start to write your project with VmPusher!
# Let's Begin!
## extend from ```PusherViewModel```

```kotlin
MainViewModel(private val timerEngine: TimerEngine) : PusherViewModel(){
 ...
}
```

## implements you BaseActivity from  ```TaskExecutable```

```kotlin
  abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    TaskExecutable {  
      ....
    }
    
```
    
## in activity owweride method and init excutePuher:
    
```kotlin
class MainActivity : BaseActivity<MainActivityBinding>()
       
       override fun onCreate(savedInstanceState: Bundle?) {
        ...
        this@MainActivity receiveTaskFrom mMainViewModel
        ...
       }

    ...
    override val vmPushExcutable = { actionId: Int ->
        performTaskForMainView(this, actionId)
    }
    ...
}
   ``` 

## create view interface for ctivty or Fragment and add Anotation:

```kotlin
    companion object {
        @BindUiListener
        interface MainView : TaskExecutable {

            @BindUiAction(actionId = 1)
            fun startClock()

            @BindUiAction(actionId = 2)
            fun showFirstQuestion()
        }
    }
```

## implement MainView in your activity:

```kotlin

 class MainActivity : BaseActivity<MainActivityBinding>(), MainViewModel.Companion.MainView{
   override fun startClock() {
    //...
   }
  
    override fun showFirstQuestion() {
      //..
    }
 }
 
```

## That's all ! Now just push you execute to view via ```ViewCommander```

```kotlin
      val viewCommander: ViewCommander = ViewCommander()

      showFirstQuestionMainView() pushBy viewCommander
      
```


