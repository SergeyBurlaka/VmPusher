# VmPusher module and "QuestApp" demo

## Let me introduce my demo ```"QuestApp"``` project with usage ```Kotlin MVVM```, ```Databindg```, ```RxJava2``` together with my personal created module ```VMPusher``` for more easly ViewModel and View interaction like in MVP style!

To send execute action from View Model to view (using ```Google Live Data``` under the hood ) with ```VmPusher``` will looks like: 

```kotlin
  startClockMainView() pushBy viewCommander
```
And that's all!
In under the hood you use ```LiveData```, but now no need to write ```observe``` method, create ```LiveData```, create ```MutableLiveData```, code to add ```Life Circle Owner``` to observe method and other boiler plate!
```VmPusher``` do it self! Amazing!

## Details

I have create module the [```vmtaskpusher```](https://github.com/SergeyBurlaka/QuestApp-MVVM/tree/feature/improve_code_gen/vmtaskpusher) 
for more easly calling method on view. (Usage LiveData and Kotlin codogeneration under the hood)
It can be usefully in some cases (For navigation, ask view ask to execute action etc)

##Usage sample 

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
    
##in activity owweride method and init excutePuher:
    
```kotlin
class MainActivity : BaseActivity<MainActivityBinding>()
       
       override fun onCreate(savedInstanceState: Bundle?) {
        ...
        this@MainActivity receiveTaskFrom mMainViewModel
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

## That's all ! Now just push you execute to view viai ```ViewCommander```

```kotlin
      val viewCommander: ViewCommander = ViewCommander()
     
      startClockMainView() pushBy viewCommander

      showFirstQuestionMainView() pushBy viewCommander
```


