# Android Ex.co SDK

This SDK provides functionality for integrating the Ex.co Player into Android applications.


## Requirements

- **Android Studio:** Integrated Development Environment (IDE) for Android development.
- **Android SDK version:** The minimum required Android SDK version is 8.0 (API level 26).


## Installation - SDK import & initialization

- **Add the following code to dependencyResolutionManagement in setting.gradle of your project**:
- **settings.gradle:**
```groovy
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven {
      url 'https://exco1.jfrog.io/artifactory/android-gradle-release/'
    }
  }
}
  ```

- **Add Dependency in your app build.gradle**:
- **build.gradle**
```groovy
implementation("com.exco:player:$versionOfSDK")
implementation("com.exco:omsdk-android:1.4.8")
```
- **The current version of sdk is 2.0.9**


- **Usage - SDK activation**
- **To get a callback from ExCoSDK object you can use ExCoMobileSDKDelegate interface**
- **In case of mobileSDKDidFailToActivate you will get an errorMessage**
- **To activate the SDK, add ExCoSDK.activate() in your application class**
```kotlin
class MyApp: Application() { 
  override fun onCreate() {
    super.onCreate()
    ExCoSDK.delegate = object : ExcoMobileSDKDelegate {
      override fun mobileSDKDidActivateSuccessfully() {
        TODO("Not yet implemented") 
      }
      override fun mobileSDKDidFailToActivate(errorMessage: String) {
        TODO("Not yet implemented") 
      }
      override fun mobileSDKDidFetchSDKJSFiles() {
        TODO("Not yet implemented") 
      }
    }
    ExCoSDK.activate()
  }
}
```

- **Declare your application class in the manifest**:
```xml
<application>
  <!-- Other configurations -->
  android:name=".MyApp"
  <!-- Other configurations -->
  </application>
```
- **View - Implementation**
- **You can create your view in XML**:

```xml
<com.exco.player.views.ExCoPlayerView
    android:id="@+id/player"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
 ```
- **To load your player you need to specify a list of parameters as part of PlayerConfiguration Class**:

```kotlin
data class PlayerConfiguration(
  var playerId: String,              //Required
  var appCategory: String?,          //Optional
  var appStoreId: String?,           //Optional
  var appStoreUrl: String?,          //Optional
  var appVersion: String?,           //Optional
  var deviceId: String?,             //Optional
  var ifa: String?,                   //Optional
  var miniPlayerType: MiniPlayerConfiguration, 
  var isProgrammatic: Boolean         //Optional By Default is false
) 
```

```kotlin
data class MiniPlayerConfiguration(
  var miniPlayerType: MiniPlayerType,   //Optional By Default is MiniPlayerType.NONE
  var isWithCloseButton: Boolean,      //Optional By Default is false
  var shouldPauseOnClose: Boolean,     //Optional By Default is false
  var isPersistent: Boolean            //Optional By Default is false
)
  ```

- **Although there are several options for the ```miniPlayerType``` field for now it is always treated as NONE**
```kotlin
val miniPlayerType = MiniPlayerType.NONE
```

- **You can define your configuration with class PlayerConfiguration or you can define it with XML**.
- **Defined with class PlayerConfiguration**

```kotlin
val mockConfiguration = PlayerConfiguration(
  playerId = "8bd33116-eacd-4b4e-a150-bedd4d71ce1c",
  appCategory = listOf("category1", "category2"),
  appStoreId = "com.example.app",
  appStoreUrl = "https://example.com/app",
  appVersion = 5,
  deviceId = "deviceId123",
  ifa = "ifaLink123",
  miniPlayerType = MiniPlayerType.NONE,
  isProgrammatic = false
)
```

- **To start playing you need to use configuration with the following function loadPlayer**.
- **Cast it to you ExCoPlayerView**.

```kotlin
player.loadPlayer(configuration)
```

- **You can define all parameters inside XML**.
- **The player will start playing right after the view is loaded on the screen**.

```xml
<com.exco.player.views.ExCoPlayerView
  android:id="@+id/player" 
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" 
  app:appCategory="@array/my_categories"
  app:appStoreId="5"
  app:appStoreUrl="https://appStoreUrl"
  app:appVersion="5"
  app:deviceId="5"
  app:ifa="ifaLink"
  app:playerId="8bd39116-eacb-4b4e-a160-bedd5d71ce1c"
  app:miniPlayer="None"/>
```

- **Where app:appCategory="@array/my_categories" is an array.xml, defined in values folder of resource**.
```xml
<resources xmlns:tools="http://schemas.android.com/tools">
  <array name="my_categories" tools:ignore="MissingDefaultResource">
    <item>Sport</item>
    <item>VideoGame</item>
    <item>Food</item>
  </array>
</resources>
```

- **You can create an ExCoPlayerView with configuration in Compose with a Composable function in this way:**:
```kotlin
@Composable
fun ComposeExCoPlayerView(configuration: PlayerConfiguration) {
  val context = LocalContext.current
  AndroidView(
    factory = { ExCoPlayerView(context) },
    modifier = Modifier.fillMaxSize()
  ) { view ->
    view.loadPlayer(playerConfiguration = configuration)
  }
}
```
- **The player will start playing right after view recomposition**.

## RecyclerView support
RecyclerView manages it's views differently which causes different lifecycle events for the view to be triggered at different times

We have created a custom Adapter and ViewHolder classes to encapsulate the adjustments needed without the developer
having to worry about it, for example:
```kotlin
enum class DataType {
    TEXT,
    EXO_PLAYER
}

sealed class ViewData(val type: DataType) {
  data class TextData(val text: String) : ViewData(DataType.TEXT)
  data class ExCoPlayerData(val configuration: PlayerConfiguration) : ViewData(DataType.EXO_PLAYER)
}

class RecyclerViewAdapter(private val dataList: List<ViewData>) : ExCoAdapter<ViewHolder>() {

  companion object {
    const val TYPE_TEXT = 1
    const val TYPE_EXO_PLAYER = 2
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      TYPE_TEXT -> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
        TextViewHolder(view)
      }
      TYPE_EXO_PLAYER -> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exco_player, parent, false)
        ExCoPlayerViewHolder(view)
      }
      else -> throw IllegalArgumentException("Invalid view type")
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val data = dataList[position]

    when (holder) {
      is TextViewHolder -> {
        data as ViewData.TextData
        holder.textView.text = data.text
      }
      is ExCoPlayerViewHolder -> {
        data as ViewData.ExCoPlayerData
        holder.exCoPlayerView.loadPlayer(data.configuration)
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (dataList[position].type) {
      DataType.TEXT -> TYPE_TEXT
      DataType.EXO_PLAYER -> TYPE_EXO_PLAYER
    }
  }

  override fun getItemCount(): Int {
    return dataList.size
  }

  inner class TextViewHolder(itemView: View) : ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.textView)
  }

  inner class ExCoPlayerViewHolder(itemView: View) : ExCoViewHolder(itemView)
}
```
With these item layouts:
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/layout_id"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <com.exco.player.views.ExCoPlayerView
        android:id="@+id/player"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</LinearLayout>
```
```xml
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:tools="http://schemas.android.com/tools"
  android:id="@+id/textView"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  tools:ignore="MissingDefaultResource" />
```
- As is visible from the example, working with `ExCoAdapter` and `ExCoViewHolder` is identical to the regular android classes
- Also note that the `ExCoViewHolder` locates the `ExCoPlayerView` automatically and exposes it as `exCoPlayerView`
  The custom view holder will locate and use the first `ExCoPlayerView` instance it finds -
  more than one player in a single item is not supported automatically!
- In case no `ExCoPlayerView` instances were found, an `IllegalStateException` will be thrown

## SDK Callbacks
- **To get a callback from ExCoPlayerView you can work with ExCoPlayerDelegate and ExCoErrorDelegate**.
### ExCoPlayerDelegate
The ExCoPlayerDelegate interface provides methods for handling various events and actions related to the ExCo player.
Developers can implement this interface to receive callbacks for player initialization.
</br>
you can get a callback on events such as playback start, ad initialization and playback, player loading, mute/unmute, ad completion/skipping, and more.
</br>
Additionally, developers can handle custom events using the unknownEvent(event: String) method.

### ExCoErrorDelegate
The ExCoErrorDelegate interface is responsible for handling errors that occur during the ExCo player's operation.
Developers can implement this interface to log ERROR-level messages using the error(msg: String) method.
</br>
By default, this method does nothing, allowing developers to customize error logging behavior as needed.

## Programmatic controlling
To gain full control over your player, you can utilize the programmatic API.
To begin working with the programmatic API, set isProgrammatic to true in your configuration object.

**Example**:
```kotlin
    val mockConfiguration = PlayerConfiguration(
      playerId = "8bd33116-eacd-4b4e-a150-bedd4d71ce1c",
      isProgrammatic = true
    )
  ```

**There is a ConfigurationOptions object to work with to setup your configuration:**

```kotlin
data class PlaylistItem(
  val id: String?,                             //Optional
  val src: String,                             //Required
  val fallback: String?,                       //Optional
  val link: String?,                           //Optional
  val title: String?,                          //Optional
  val description: String?,                    //Optional
  val poster: String?,                         //Optional
  val duration: Double?,                       //Optional
  val tags: List<String>?,                     //Optional
  val keywords: List<String>?,                 //Optional
  val customParams: Map<String,Any>?           //Optional
)

data class ContentVideoSettings(
    val playFirst: List<PlaylistItem>?,        //Optional
    val replacePlaylist: Boolean?,             //Optional
    val tags: List<String>?,                   //Optional
    val playlistId: String?,                   //Optional
    val playlist: List<PlaylistItem>?,         //Optional
    val playlistIndex: Int?,                   //Optional
    val customParams: Map<String,Any>?         //Optional
)

enum class PlaybackMode(val value: String) {
    CLICK_TO_PLAY("click-to-play"),
    AUTO_PLAY("auto-play"),
    PLAY_IN_VIEW("play-in-view")
}
  
data class ConfigurationOptions(
    val playbackMode: PlaybackMode?,           //Optional
    val autoPlay: Boolean?,                    //Optional
    val mute: Boolean?,                        //Optional
    val showAds: Boolean?,                     //Optional
    val customParams: Map<String,Any>?,        //Optional
    val content: ContentVideoSettings?,        //Optional
)
  ```

**To initialize the player with player configuration, call initProgrammaticPlayer on your player with a configuration created using the ConfigurationOptions object**.

```kotlin
fun init(configurationOptionsObject: ConfigurationOptions?)
```

**Example**:
```kotlin
val config = ConfigurationOptions(
    playbackMode = PlaybackMode.CLICK_TO_PLAY,
    autoPlay = false,
    mute = false,
    showAds = true,
    customParams = mapOf("customColor" to "red"),
    content = ContentVideoSettings(
        playFirst = listOf(
            PlaylistItem(
                id = "fa375-264b-4a86-809c-bbfc191532c1",
                src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
            ),
            PlaylistItem(
                id = "fa375-264b-4a86-809c-bbfc191532c2",
                src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
            )
        ),
        tags = listOf("Sport","Cinema"),
        playlist = listOf(
            PlaylistItem(
                id = "fa375-264b-4a86-809c-bbfc191532c1",
                src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
            ),
            PlaylistItem(
                id = "fa375-264b-4a86-809c-bbfc191532c2",
                src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
            )
        )
    )
)

binding.player.init(config)
  ```

After you initialize the player with player configurationOptions you can start to work with API.
There is a list of possible methods:
```kotlin
    fun play()
    fun pause()
    fun getPlaylistIndex(callback: (Int) -> Unit)
    fun getContentPosition(callback: (Float) -> Unit)
    fun getPlaylistItem(callback: (PlaylistItem) -> Unit)
    fun setPlaylistIndex(playlistIndex: Int)
    fun addPlaylistItem(playlistItems: List<PlaylistItem>,insertAtIndex: Int)
    fun destroy()
  ```

### Api Usage Example
Note: play,pause,getPlaylistIndex,getContentPosition,getPlaylistItem,setPlaylistIndex,addPlaylistItem,destroy are XML buttons
```kotlin
play.setOnClickListener {
  player.play()
}
pause.setOnClickListener {
  player.pause()
}
getPlaylistIndex.setOnClickListener {
  player.getPlaylistIndex {
    Log.i("PlayerListIndex", it.toString())
  }
}
getContentPosition.setOnClickListener {
  binding.player.getContentPosition {
    Log.i("GetContentPosition", it.toString())
  }
}
getPlaylistItem.setOnClickListener {
  binding.player.getPlaylistItem { playlistItem ->
    Log.i("PlayerListItem", playlistItem.toString())
  }
}
setPlaylistIndex.setOnClickListener {
  binding.player.setPlaylistIndex(0)
}
addPlaylistItem.setOnClickListener {
  binding.player.addPlaylistItem(
    listOf<PlaylistItem>(
      PlaylistItem(
        "VideoExample",
        "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
      )
    ), 0
  )
}
destroy.setOnClickListener {
  binding.player.destroy()
}
  ```

## Important
- Be sure that nothing is on the layer in front of the view and the end user will definitely see the video and the advertisement.
- Be sure that if you are setting size manually you have to follow aspect ratio 16:9, where 16 is width, 9 is height.

## Version

The current version of sdk is 2.0.7

## License
This project is licensed under the [License Name] - see the LICENSE.md file for details.

```csharp
Replace `$versionOfSDK` with the actual version of the SDK you are using.
```
