
# Getting Started

Diuit UI Kit is an open source framework of customizable UI components for using Diuit Messaging API, which is designed to build messaging feature into any mobile apps quickly. This repository contains the entire UI library for modern messaging apps. If you want to see how the UI looks like or how Diuit API works, please refer to our [Demo App].


This document was updated at: 2016-06-06 07:28:00+00


## Prerequisites

- We do not support Java outside of Android at the moment.
- A recent version of the Android SDK
- We support all Diuit SDK versions since Diuit API 1.1.3+ (Android 1.1.3 & above).


**Maven**

1.  Navigate to your build.gradle file at the app level (not project level) and ensure that you include the following:

```java
repositories {
    maven {
      url "https://dl.bintray.com/duolc/maven"
    }
}
```

2. Add `compile 'com.duolc.diuitapi:messageui:0.1.6'` to the dependencies of your project
3. In the Android Studio Menu: Tools -> Android -> Sync Project with Gradle Files



# Features

There are several main component we provide in Diuit UI Kit,

1. Page: The entire page view, which usually an extension from RelativeLayout. Developers can use xml to set up attributes or use the instance of the view to add or remove views directly.

2. ItemView: For [DiuitUser](http://api.diuit.com/doc/en/guideline.html#user), [DiuitChat](http://api.diuit.com/doc/en/guideline.html#chat) and [DiuitMessage](http://api.diuit.com/doc/en/guideline.html#class-message), we provide ItemView to help developers quickly generate view components.

3. Utility: we also define several UI components for chat interface.



# Overview

In Quick Start, we list all the components that will be used in Diuit UI Kit. It will help you find the function of each component by its class name and link.


## Page

| ClassName                   | Type           | Default Attributes                        | Example                               |
----------------------------- | -------------- | ----------------------------------------- | ------------------------------------- |
| DiuitChatsRecyclerView      | RelativeLayout | R.style.DiuitChatsRecyclerViewDefault     | [R.layout.activity_chats_list](https://github.com/diuitAPI/diuit.uikit.demo.android/tree/master/app/src/main/res/layout/activity_chats_list.xml)          |
| DiuitMessagesListView       | RelativeLayout | R.style.DiuitMessagesListViewDefault      | [R.layout.activity_messages_list](https://github.com/diuitAPI/diuit.uikit.demo.android/tree/master/app/src/main/res/layout/activity_messages_list.xml)       |
| DiuitGroupChatSettingView   | RelativeLayout | R.style.DiuitGroupChatSettingViewDefault  | [R.layout.activity_group_chat_setting](https://github.com/diuitAPI/diuit.uikit.demo.android/tree/master/app/src/main/res/layout/activity_group_chat_setting.xml)  |
| DiuitParticipantSettingView | RelativeLayout | R.style.DiuitParticipantSettingViewDefault| [R.layout.activity_direct_chat_setting](https://github.com/diuitAPI/diuit.uikit.demo.android/tree/master/app/src/main/res/layout/activity_direct_chat_setting.xml) |
| DiuitMessagePreviewFactory  | RelativeLayout | NULL                                      | [R.layout.activity_preview](https://github.com/diuitAPI/diuit.uikit.demo.android/tree/master/app/src/main/res/layout/activity_preview.xml)             |



## Adapter

| ClassName                     | Type                               | Default                            |
------------------------------- | ---------------------------------- | ---------------------------------- |
| DiuitChatsRecyclerViewAdapter | RecyclerView.Adapter               | To go with DiuitChatsRecyclerView  |
| DiuitMessagesListViewAdapter  | ArrayAdapter<DiuitMessageListCell> | To go with DiuitMessagesListView   |



## ItemView

| ClassName                   | Type           | Default Attributes                        |
----------------------------- | -------------- | ----------------------------------------- |
| DiuitChatView               | RelativeLayout | R.style.DiuitChatViewDefault              |
| DiuitMessageView            | RelativeLayout | R.style.DiuitMessageViewDefault           |
| DiuitMessageContentFactory  | RelativeLayout | NULL                                      |
| DiuitMemberView             | RelativeLayout | R.style.DiuitMemberViewDefault            |



## Factory
In the Preview Page and DiuitMessageView, we provide DiuitPreviewFacotry and DiuitMessageContentFactory to handle different types of messages.
For example, this line of code allows you to preview a message that contains a photo link. [Read more](# DiuitMessagePreviewFactory)


```Java
DiuitMessageContentFactory.bindMessage().image().setAttribute().load()
```



## Other UI Components

| ClassName                   | Default Attributes                         | Description                  |
----------------------------- | ------------------------------------------ | ---------------------------- |
| DiuitTypingView             | R.style.DiuitChatViewDefault               | If receiving user.typing event, DiuitMessagesListViewAdapter  will show this typing view autonatically in 1-to-1 chat |
| DiuitImageView              | ImageView                                  | Make ImageView circular by calling circle()|
|DiuitPullToLoadMoreListView  | R.style.DiuitPullToLoadMoreListViewDefault | pull to load more messages                 |



## Utils

| ClassName                   | Description                                                                             |
----------------------------- | --------------------------------------------------------------------------------------- |
| DiuitsystemType             | List all message system type. You can check whether the message is from system or not   |
| DiuitMessageDownloadTask    | Combine DownloadManager.Request and the meta of the message for downloading files       |
| DiuitMessageListCell        |




# Page

There are three main pages: Chat List Page, Message List Page, and Chat Setting Page. All you need to do is assign these pages a DiuitChat object by calling `bindChat()`. You can also use XML to define your attributes, or use Java code to access the page directly.

## DiuitChatsRecyclerView

<image src="./images/DiuitChatsRecyclerView.png"/>

You can customize attributes from xml.

```xml
<com.duolc.diuitapi.messageui.page.DiuitChatsRecyclerView
android:id="@+id/diuitChatListView"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:colorBackground="@color/white"
app:diuListDivider="1dp"
app:diuTitleTextColor="@color/black">
</com.duolc.diuitapi.messageui.page.DiuitChatsRecyclerView>
```

> All Attributes of DiuitChatsRecyclerView

```xml
<resources>
<declare-styleable name="DiuitChatsRecyclerView">
<attr name="colorBackground" format="color"/>
<attr name="diuListDivider" format="dimension"/>
<attr name="diuTitleMinHeight" format="dimension"/>
<attr name="diuTitleBackground" format="color"/>
<attr name="diuTitleElevation" format="dimension"/>
<attr name="diuTitleTextColor" format="color"/>
<attr name="diuTitleText" format="string"/>
<attr name="diuTitleTextSize" format="dimension"/>
<attr name="diuTitleTextMargin" format="dimension"/>
</declare-styleable>
</resources>
```

You can dynamically use the view object with:

```java
DiuitChatsRecyclerView diuitChatsRecyclerView = (DiuitChatsRecyclerView) this.findViewById(R.id.diuitChatListView);
diuitChatsRecyclerView.bindChats(diuitChats, new DiuitChatsRecyclerViewAdapter.OnItemClickListener() {
@Override
public void onItemClick(DiuitChat diuitChat) {
/*Put your code*/
}
});
```

Or custimize the adapter

```java
DiuitChatsRecyclerView diuitChatsRecyclerView = (DiuitChatsRecyclerView) this.findViewById(R.id.diuitChatListView);
diuitChatsRecyclerView.bindChatsByAdapter(new LinearLayoutManager(ctx), new RecyclerView.Adapter() {
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*Put your code*/
}

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
/*Put your code*/
}
});
```

In advance, if you want to hide the title layout, you can use the following code:

```Java
diuitChatsRecyclerView.getTitleView().setVisibility(View.GONE);
```



## DiuitMessagesListView

<image src="./images/DiuitMessagesRecyclerView.png"/>

DiuitMessagesListView which contains input layout, title layout and message list view is slightly more complicated. We register a receiving message listener and handle different type messages automatically.
Less flexible but convenience.


You can customize attributes from xml.

```java
<com.duolc.diuitapi.messageui.page.DiuitMessagesListView
android:id="@+id/diuitChatListView"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:colorBackground="@color/white"
app:diuListDivider="1dp"
app:diuTitleElevation="1dp"
app:diuTitleTextColor="@color/black">
</com.duolc.diuitapi.messageui.page.DiuitMessagesListView>
```

You also can dynamically adjust the attributes of view by calling `setAttributes()`

```java
DiuitMessagesListView DiuitMessagesListView = (DiuitMessagesListView) this.findViewById(R.id.view);
DiuitMessagesListView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE);
```


> All Attributes of DiuitMessagesListView

```xml
<resources>
<declare-styleable name="DiuitMessagesListView">
<attr name="colorBackground" format="color"/>
<attr name="diuListDivider" format="dimension"/>

<attr name="diuTitleMinHeight" format="dimension"/>
<attr name="diuTitleBackground" format="color"/>
<attr name="diuTitleElevation" format="dimension"/>
<attr name="diuTitleTextColor" format="color"/>
<attr name="diuTitleText" format="string"/>
<attr name="diuTitleTextSize" format="dimension"/>
<attr name="diuTitleTextMargin" format="dimension"/>

<attr name="diuInputLayoutMinHeight" format="dimension"/>

<attr name="diuSettingBtnSize" format="dimension"/>
<attr name="diuSettingBtnTopMargin" format="dimension"/>
<attr name="diuSettingBtnLeftMargin" format="dimension"/>
<attr name="diuSettingBtnRightMargin" format="dimension"/>
<attr name="diuSettingBtnBottomMargin" format="dimension"/>
<attr name="diuSettingBtnSrc" format="reference"/>
<attr name="diuSettingBtnBackground" format="reference"/>

<attr name="diuAtachmentBtnSize" format="dimension"/>
<attr name="diuAtachmentBtnTopMargin" format="dimension"/>
<attr name="diuAtachmentBtnLeftMargin" format="dimension"/>
<attr name="diuAtachmentBtnRightMargin" format="dimension"/>
<attr name="diuAtachmentBtnBottomMargin" format="dimension"/>
<attr name="diuAtachmentBtnSrc" format="reference"/>
<attr name="diuAtachmentBtnBackground" format="reference"/>

<attr name="diuSendMsgBtnText" format="string"/>
<attr name="diuSendMsgBtnTextEnableColor" format="color"/>
<attr name="diuSendMsgBtnTextDisableColor" format="color"/>
<attr name="diuSendMsgBtnTextSize" format="dimension"/>
<attr name="diuSendMsgBtnSize" format="dimension"/>
<attr name="diuSendMsgBtnTopMargin" format="dimension"/>
<attr name="diuSendMsgBtnLeftMargin" format="dimension"/>
<attr name="diuSendMsgBtnBottomMargin" format="dimension"/>
<attr name="diuSendMsgBtnRightMargin" format="dimension"/>
<attr name="diuSendMsgBtnSrc" format="color"/>
<attr name="diuSendMsgBtnBackground" format="reference"/>

<attr name="diuMsgInputMinHeight" format="dimension"/>
<attr name="diuMsgInputBackground" format="reference"/>
<attr name="diuMsgInputHint" format="string"/>
<attr name="diuMsgInputHintColor" format="color"/>
<attr name="diuMsgInputTextSize" format="dimension"/>
<attr name="diuMsgInputTextColor" format="color"/>
<attr name="diuMsgInputTopMargin" format="dimension"/>
<attr name="diuMsgInputTextMaxLines" format="integer"/>
<attr name="diuMsgInputTextLeftPadding" format="dimension"/>
<attr name="diuMsgInputTextTopPadding" format="dimension"/>
<attr name="diuMsgInputTextRightPadding" format="dimension"/>
<attr name="diuMsgInputTextBottomPadding" format="dimension"/>
<attr name="diuMsgInputLeftMargin" format="dimension"/>
<attr name="diuMsgInputBottomMargin" format="dimension"/>
<attr name="diuMsgInputRightMargin" format="dimension"/>
</declare-styleable>
</resources>
```

You can bind a chat object by calling `bind()`

```java
DiuitMessagesListView.bind(DiuitChat);
```

In addition, you can set up the amount of displayed messages while fetching from server, by default the number of displayed message is 10.

```java
DiuitMessagesListView.setFetchCountForMessages(count);
```

DiuitMessagesListView will show all messages by default. You can also call the function `setIgnoreSystemMessages()` to filter out those system messages you don't want them to show up.


```java
DiuitMessagesListView.setIgnoreSystemMessages(DiuitSystemType.KICK, DiuitSystemType.UPDATE_META, DiuitSystemType.UPDATE_WHITELIST)
```

In the end , you have to call `load()` to load your settings.

```java
DiuitMessagesListView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bind(DiuitChat).load();
```

### DiuitSystemType
In this enum, we defined all kinds of system messages. For DiuitSystemType.TYPING, we use [TypingIndicator](###TypingIndicator);for DiuitSystemType.READED, we update message view by default;for other system messages, we use a textview to show. For more information, please refer to the DiuitSystemType class in Diuit UI Kit


### TypingIndicator
In DiuitMessagesListView, we provide the UI for TypingIndicator. In a one-on-one chat, if you recevie a `user.typing` message, we will add DiuitTypingView as the footer of listview by default.

If you don't want to show typing indicator in a one-on-one chat, you can use the following code to disable it:

```Java
DiuitMessagesListView.enableDefaultTypingIndicatorUI(false)
```

Also, you can use `setTypingIndicatorTimer(long millis)` to set up the period between two `user.typing` system messages.


```Java
DiuitMessagesListView.setTypingIndicatorTimer(5000)
```


You also can dynamically adjust the attributes of view by calling `setAttributes()`

```java
DiuitTypingView diuitTypingView = (DiuitTypingView) this.findViewById(R.id.view);
diuitTypingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE);
```

> All the Attributes of DiuitMessagesListView

```xml
<declare-styleable name="DiuitTypingView">
<attr name="diuTypingViewWidth" format="dimension|integer"/>
<attr name="diuTypingViewHeight" format="dimension|integer"/>
<attr name="diuTypingViewMinHeight" format="dimension"/>
<attr name="diuTypingViewBackground" format="reference"/>

<attr name="diuTypingTextColor" format="color"/>
<attr name="diuTypingText" format="string"/>
<attr name="diuTypingTextSize" format="dimension"/>
<attr name="diuTypingTextGravity" format="integer"/>
<attr name="diuTypingTextTopPadding" format="dimension"/>
<attr name="diuTypingTextLeftPadding" format="dimension"/>
<attr name="diuTypingTextRightPadding" format="dimension"/>
<attr name="diuTypingTextBottomPadding" format="dimension"/>
<attr name="diuTypingTextMaxLines" format="integer"/>
<attr name="diuTypingTimeFormat" format="string"/>

<attr name="diuTypingIconWidth" format="dimension|integer"/>
<attr name="diuTypingIconHeight" format="dimension|integer"/>
<attr name="diuTypingIconTopMargin" format="dimension"/>
<attr name="diuTypingIconBottomMargin" format="dimension"/>
<attr name="diuTypingIconRightMargin" format="dimension"/>
<attr name="diuTypingIconLeftMargin" format="dimension"/>
<attr name="diuTypingIconSrc" format="reference"/>
<attr name="diuTypingIconBackground" format="reference"/>
</declare-styleable>
```

You can get the typingIdicatorView from DiuitMessagesListView by calling:

```Java
DiuitMessagesListView.getTypingIndicatorView()
```




## DiuitChatSettingView
It is important to provide a setting page for users to manage chat rooms. Therefore, we also provide `DiuitGroupChatSettingView` and `DiuitParticipantSettingView` as the setting page for group chat and direct(1-one-1) chat.


### DiuitGroupChatSettingView

<image src="./images/DiuitGroupChatSettingView.png"/>

You can customize attributes from xml.

```xml
<com.duolc.diuitapi.messageui.setting.DiuitGroupChatSettingView
android:id="@+id/diuitSettingView"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:diuSettingChatLayoutBackgroundColor="@color/black"
app: diuTitleText="Setting Page">
</com.duolc.diuitapi.messageui.setting.DiuitGroupChatSettingView>
```


You also can dynamically adjust the attributes of view by calling `setAttributes()`

```java
DiuitGroupChatSettingView chatSettingView = (DiuitGroupChatSettingView) this.findViewById(R.id.view);
chatSettingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE);
```


> All Attributes of DiuitGroupChatSettingView

```xml
<declare-styleable name="DiuitGroupChatSettingView">
<attr name="colorBackground" format="color"/>
<attr name="diuListDivider" format="dimension"/>

<attr name="diuTitleMinHeight" format="dimension"/>
<attr name="diuTitleBackground" format="color"/>
<attr name="diuTitleElevation" format="dimension"/>
<attr name="diuTitleTextColor" format="color"/>
<attr name="diuTitleText" format="string"/>
<attr name="diuTitleTextSize" format="dimension"/>
<attr name="diuTitleTextMargin" format="dimension"/>

<attr name="diuSettingChatLayoutWidth" format="dimension|integer"/>
<attr name="diuSettingChatLayoutHeight" format="dimension|integer"/>
<attr name="diuSettingChatLayoutMinHeight" format="dimension"/>
<attr name="diuSettingChatLayoutBackgroundColor" format="color"/>
<attr name="diuSettingChatLayoutTopMargin" format="dimension"/>
<attr name="diuSettingChatLayoutRightMargin" format="dimension"/>
<attr name="diuSettingChatLayoutLeftMargin" format="dimension"/>
<attr name="diuSettingChatLayoutBottomMargin" format="dimension"/>

<attr name="diuSettingChatIconWidth" format="dimension|integer"/>
<attr name="diuSettingChatIconHeight" format="dimension|integer"/>
<attr name="diuSettingChatIconLayoutGravity" format="integer"/>
<attr name="diuSettingChatIconTopMargin" format="dimension"/>
<attr name="diuSettingChatIconBottomMargin" format="dimension"/>
<attr name="diuSettingChatIconRightMargin" format="dimension"/>
<attr name="diuSettingChatIconLeftMargin" format="dimension"/>
<attr name="diuSettingChatIconSrc" format="reference"/>

<attr name="diuSettingChatNameTextWidth" format="dimension|integer"/>
<attr name="diuSettingChatNameTextHeight" format="dimension|integer"/>
<attr name="diuSettingChatNameTextColor" format="color"/>
<attr name="diuSettingChatNameText" format="string"/>
<attr name="diuSettingChatNameTextSize" format="dimension"/>
<attr name="diuSettingChatNameTextLayoutGravity" format="integer"/>
<attr name="diuSettingChatNameTextTopMargin" format="dimension"/>
<attr name="diuSettingChatNameTextRightMargin" format="dimension"/>
<attr name="diuSettingChatNameTextLeftMargin" format="dimension"/>
<attr name="diuSettingChatNameTextBottomMargin" format="dimension"/>

<attr name="diuSettingMembersLayoutWidth" format="dimension|integer"/>
<attr name="diuSettingMembersLayoutHeight" format="dimension|integer"/>
<attr name="diuSettingMembersLayoutMinHeight" format="dimension"/>
<attr name="diuSettingMembersLayoutBackground" format="color"/>
<attr name="diuSettingMembersLayoutTopMargin" format="dimension"/>
<attr name="diuSettingMembersLayoutRightMargin" format="dimension"/>
<attr name="diuSettingMembersLayoutLeftMargin" format="dimension"/>
<attr name="diuSettingMembersLayoutBottomMargin" format="dimension"/>

<attr name="diuMembersHeaderTextWidth" format="dimension|integer"/>
<attr name="diuMembersHeaderTextHeight" format="dimension|integer"/>
<attr name="diuMembersHeaderTextMinHeight" format="dimension"/>
<attr name="diuMembersHeaderTextBackground" format="color"/>
<attr name="diuMembersHeaderTextTopMargin" format="dimension"/>
<attr name="diuMembersHeaderTextRightMargin" format="dimension"/>
<attr name="diuMembersHeaderTextLeftMargin" format="dimension"/>
<attr name="diuMembersHeaderTextBottomMargin" format="dimension"/>
<attr name="diuMembersHeaderTextColor" format="color"/>
<attr name="diuMembersHeaderText" format="string"/>
<attr name="diuMembersHeaderTextSize" format="dimension"/>
<attr name="diuMembersHeaderTextGravity" format="integer"/>

<attr name="diuLeaveBtnWidth" format="dimension|integer"/>
<attr name="diuLeaveBtnHeight" format="dimension|integer"/>
<attr name="diuLeaveBtnText" format="string"/>
<attr name="diuLeaveBtnTextColor" format="color"/>
<attr name="diuLeaveBtnTextSize" format="dimension"/>
<attr name="diuLeaveBtnTextGravity" format="integer"/>
<attr name="diuLeaveBtnTextTopPadding" format="dimension"/>
<attr name="diuLeaveBtnTextLeftPadding" format="dimension"/>
<attr name="diuLeaveBtnTextBottomPadding" format="dimension"/>
<attr name="diuLeaveBtnTextRightPadding" format="dimension"/>
<attr name="diuLeaveBtnMinHeight" format="dimension"/>
<attr name="diuLeaveBtnTopMargin" format="dimension"/>
<attr name="diuLeaveBtnLeftMargin" format="dimension"/>
<attr name="diuLeaveBtnBottomMargin" format="dimension"/>
<attr name="diuLeaveBtnRightMargin" format="dimension"/>
<attr name="diuLeaveBtnBackground" format="reference"/>
</declare-styleable>
```


You can bind a chat object by calling `bindChat()`

```java
diuitGroupChatSettingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindChat(DiuitChat);
```

Calling `load()` to load your settings.

```java
diuitGroupChatSettingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindChat(DiuitChat).load();
```


### DiuitParticipantSettingView

<image src="./images/DiuitParticipantSettingView.png"/>

You can customize attributes from xml.

```java
<com.duolc.diuitapi.messageui.setting.DiuitParticipantSettingView
android:id="@+id/diuitSettingView"
android:layout_width="match_parent"
android:layout_height="match_parent"
app: diuTitleTextColor="@color/black"
app: diuTitleText="Setting Page">
</com.duolc.diuitapi.messageui.setting.DiuitParticipantSettingView>
```


You also can dynamically adjust the attributes of view by calling `setAttributes()`

```java
DiuitParticipantSettingView chatSettingView = (DiuitParticipantSettingView) this.findViewById(R.id.view);
chatSettingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE);
```


> All Attributes of DiuitParticipantSettingView

```xml
<declare-styleable name="DiuitParticipantSettingView">
<attr name="colorBackground" format="color"/>
<attr name="diuListDivider" format="dimension"/>

<attr name="diuTitleMinHeight" format="dimension"/>
<attr name="diuTitleBackground" format="color"/>
<attr name="diuTitleElevation" format="dimension"/>
<attr name="diuTitleTextColor" format="color"/>
<attr name="diuTitleText" format="string"/>
<attr name="diuTitleTextSize" format="dimension"/>
<attr name="diuTitleTextMargin" format="dimension"/>

<attr name="diuSettingUserLayoutWidth" format="dimension|integer"/>
<attr name="diuSettingUserLayoutHeight" format="dimension|integer"/>
<attr name="diuSettingUserLayoutMinHeight" format="dimension"/>
<attr name="diuSettingUserLayoutBackgroundColor" format="color"/>
<attr name="diuSettingUserLayoutTopMargin" format="dimension"/>
<attr name="diuSettingUserLayoutRightMargin" format="dimension"/>
<attr name="diuSettingUserLayoutLeftMargin" format="dimension"/>
<attr name="diuSettingUserLayoutBottomMargin" format="dimension"/>

<attr name="diuSettingUserIconWidth" format="dimension|integer"/>
<attr name="diuSettingUserIconHeight" format="dimension|integer"/>
<attr name="diuSettingUserIconLayoutGravity" format="integer"/>
<attr name="diuSettingUserIconTopMargin" format="dimension"/>
<attr name="diuSettingUserIconBottomMargin" format="dimension"/>
<attr name="diuSettingUserIconRightMargin" format="dimension"/>
<attr name="diuSettingUserIconLeftMargin" format="dimension"/>
<attr name="diuSettingUserIconSrc" format="reference"/>
<attr name="diuSettingUserIconBackground" format="reference"/>

<attr name="diuSettingUserNameTextWidth" format="dimension|integer"/>
<attr name="diuSettingUserNameTextHeight" format="dimension|integer"/>
<attr name="diuSettingUserNameTextColor" format="color"/>
<attr name="diuSettingUserNameText" format="string"/>
<attr name="diuSettingUserNameTextSize" format="dimension"/>
<attr name="diuSettingUserNameTextLayoutGravity" format="integer"/>
<attr name="diuSettingUserNameTextTopMargin" format="dimension"/>
<attr name="diuSettingUserNameTextRightMargin" format="dimension"/>
<attr name="diuSettingUserNameTextLeftMargin" format="dimension"/>
<attr name="diuSettingUserNameTextBottomMargin" format="dimension"/>

<attr name="diuSettingBlockLayoutWidth" format="dimension|integer"/>
<attr name="diuSettingBlockLayoutHeight" format="dimension|integer"/>
<attr name="diuSettingBlockLayoutMinHeight" format="dimension"/>
<attr name="diuSettingBlockLayoutBackgroundColor" format="color"/>
<attr name="diuSettingBlockLayoutTopMargin" format="dimension"/>
<attr name="diuSettingBlockLayoutRightMargin" format="dimension"/>
<attr name="diuSettingBlockLayoutLeftMargin" format="dimension"/>
<attr name="diuSettingBlockLayoutBottomMargin" format="dimension"/>

<attr name="diuSettingBlockTextWidth" format="dimension|integer"/>
<attr name="diuSettingBlockTextHeight" format="dimension|integer"/>
<attr name="diuSettingBlockTextColor" format="color"/>
<attr name="diuSettingBlockText" format="string"/>
<attr name="diuSettingBlockTextSize" format="dimension"/>
<attr name="diuSettingBlockTextTopMargin" format="dimension"/>
<attr name="diuSettingBlockTextRightMargin" format="dimension"/>
<attr name="diuSettingBlockTextLeftMargin" format="dimension"/>
<attr name="diuSettingBlockTextBottomMargin" format="dimension"/>

<attr name="diuSettingBlockSwitchWidth" format="dimension|integer"/>
<attr name="diuSettingBlockSwitchHeight" format="dimension|integer"/>
<attr name="diuSettingBlockSwitchTopMargin" format="dimension"/>
<attr name="diuSettingBlockSwitchBottomMargin" format="dimension"/>
<attr name="diuSettingBlockSwitchRightMargin" format="dimension"/>
<attr name="diuSettingBlockSwitchLeftMargin" format="dimension"/>
<attr name="diuSettingBlockSwitchUncheckThumbColor" format="color"/>
<attr name="diuSettingBlockSwitchUncheckTrackColor" format="color"/>
<attr name="diuSettingBlockSwitchCheckThumbColor" format="color"/>
<attr name="diuSettingBlockSwitchCheckTrackColor" format="color"/>
</declare-styleable>
```

You can bind a chat object by calling `bindChat()`

```java
diuitParticipantSettingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindChat(DiuitChat);
```

Calling `load()` to load your settings.

```java
diuitParticipantSettingView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindChat(DiuitChat).load();
```



# ItemView

For DiuitUser，DiuitChat, and DiuitMessage, we provide ItemView for different types of chat and message to generate UI components quickly.


## DiuitChatView


<image src="./images/DiuitChatView.png"/>

In DiuitChatView, we parse the meta of DiuitChat by default. We will use the value of `name` and `url` as the chat name and icon url, As the following format:

```java
diuitChat.meta => { "name" : "ChatRoomName", "url" : "http://iconLink", ...}
```

You can also dynamically adjust the attributes of view by calling `setAttributes()`

```java
DiuitChatView diuitChatView = (DiuitChatView) this.findViewById(R.id.view);
diuitChatView(R.style.YOUR_CUSTOMIZED_STYLE);
```

> All Attributes of DiuitChatView

```xml
<declare-styleable name="DiuitChatView">
<attr name="diuChatBackground" format="reference"/>
<attr name="diuChatMinHeight" format="dimension"/>

<attr name="diuChatIconSize" format="dimension"/>
<attr name="diuChatIconTopMargin" format="dimension"/>
<attr name="diuChatIconBottomMargin" format="dimension"/>
<attr name="diuChatIconRightMargin" format="dimension"/>
<attr name="diuChatIconLeftMargin" format="dimension"/>
<attr name="diuChatIconSrc" format="reference"/>
<attr name="diuChatIconPlaceHolder" format="reference"/>

<attr name="diuChatTitleTextColor" format="color"/>
<attr name="diuChatTitleText" format="string"/>
<attr name="diuChatTitleTextSize" format="dimension"/>
<attr name="diuChatTitleTextTopMargin" format="dimension"/>
<attr name="diuChatTitleTextRightMargin" format="dimension"/>
<attr name="diuChatTitleTextLeftMargin" format="dimension"/>
<attr name="diuChatTitleTextBottomMargin" format="dimension"/>
<attr name="diuChatTitleTextMaxLines" format="integer"/>

<attr name="diuChatSubTitleTextColor" format="color"/>
<attr name="diuChatSubTitleText" format="string"/>
<attr name="diuChatSubTitleTextSize" format="dimension"/>
<attr name="diuChatSubTitleTextTopMargin" format="dimension"/>
<attr name="diuChatSubTitleTextLeftMargin" format="dimension"/>
<attr name="diuChatSubTitleTextRightMargin" format="dimension"/>
<attr name="diuChatSubTitleTextBottomMargin" format="dimension"/>
<attr name="diuChatSubTitleTextMaxLines" format="integer"/>

<attr name="diuChatLastMsgTimeTextColor" format="color"/>
<attr name="diuChatLastMsgTimeText" format="string"/>
<attr name="diuChatLastMsgTimeTextSize" format="dimension"/>
<attr name="diuChatLastMsgTimeTextTopMargin" format="dimension"/>
<attr name="diuChatLastMsgTimeTextBottomMargin" format="dimension"/>
<attr name="diuChatLastMsgTimeTextLeftMargin" format="dimension"/>
<attr name="diuChatLastMsgTimeTextRightMargin" format="dimension"/>
<attr name="diuChatLastMsgTimeFormat" format="string"/>
</declare-styleable>
```


You can bind a chat object by calling `bindChat()`

```java
diuitChatView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindChat(DiuitChat);
```

Calling `load()` to load your settings.

```java
diuitChatView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindChat(DiuitChat).load();
```



## DiuitMessageView

DiuitMessageView contains user's display name, icon, message meta and DiuitMessageContentFactory. By default, DiuitMessageView parses the DiuitMessage object, check whether the sender's meta contains `name` and `url` or not, and use the value of `name` and `url` as the displayname and sender icon link.

<image src="./images/DiuitMessageViewText.png"/>
<image src="./images/DiuitMessageViewText1.png"/>

```java
diuitUser.meta => { "name" : "Sender Name", "url" : "http://userIconLink", ...}
```

You can also dynamically adjust the attributes of view by calling `setAttributes()`

```java
DiuitMessageView diuitMessageView = (DiuitMessageView) this.findViewById(R.id.view);
diuitMessageView(R.style.YOUR_CUSTOMIZED_STYLE);
```

You can bind a chat object by calling `bindMessage()`

```java
diuitMessageView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindMessage(DiuitMessage);
```

Calling `load()`

```java
diuitMessageView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindMessage(DiuitMessage).load();
```


> All Attributes of DiuitChatView

```xml
<declare-styleable name="DiuitMessageView">
<attr name="diuMsgBackground" format="reference"/>
<attr name="diuMsgMinHeight" format="dimension"/>
<attr name="diuMsgSpacing" format="dimension"/>

<attr name="diuMsgOthersIconSize" format="dimension"/>
<attr name="diuMsgOthersIconTopMargin" format="dimension"/>
<attr name="diuMsgOthersIconBottomMargin" format="dimension"/>
<attr name="diuMsgOthersIconRightMargin" format="dimension"/>
<attr name="diuMsgOthersIconLeftMargin" format="dimension"/>
<attr name="diuMsgOthersIconSrc" format="reference"/>

<attr name="diuMsgUserTextColor" format="color"/>
<attr name="diuMsgUserText" format="string"/>
<attr name="diuMsgUserTextSize" format="dimension"/>
<attr name="diuMsgUserTextTopMargin" format="dimension"/>
<attr name="diuMsgUserTextLeftMargin" format="dimension"/>
<attr name="diuMsgUserTextRightMargin" format="dimension"/>
<attr name="diuMsgUserTextBottomMargin" format="dimension"/>

<attr name="diuMsgSContentBackground" format="reference"/>
<attr name="diuMsgSContentWidth" format="dimension|integer"/>
<attr name="diuMsgSContentHeight" format="dimension|integer"/>
<attr name="diuMsgSContentMinHeight" format="dimension"/>
<attr name="diuMsgSContentTopMargin" format="dimension"/>
<attr name="diuMsgSContentRightMargin" format="dimension"/>
<attr name="diuMsgSContentLeftMargin" format="dimension"/>
<attr name="diuMsgSContentBottomMargin" format="dimension"/>

<attr name="diuMsgSContentTextSize" format="dimension"/>
<attr name="diuMsgSContentTextColor" format="reference"/>
<attr name="diuMsgSContentTextBackground" format="reference"/>
<attr name="diuMsgSContentTextPadding" format="dimension"/>
<attr name="diuMsgSContentTextEms" format="integer"/>
<attr name="diuMsgSContentImageSize" format="dimension"/>
<attr name="diuMsgSContentImagePlaceHolder" format="reference"/>
<attr name="diuMsgSContentImageCornerRadius" format="float"/>
<attr name="diuMsgSendingAlpha" format="float"/>

<attr name="diuMsgRContentBackground" format="reference"/>
<attr name="diuMsgRContentWidth" format="dimension|integer"/>
<attr name="diuMsgRContentHeight" format="dimension|integer"/>
<attr name="diuMsgRContentMinHeight" format="dimension"/>
<attr name="diuMsgRContentTopMargin" format="dimension"/>
<attr name="diuMsgRContentRightMargin" format="dimension"/>
<attr name="diuMsgRContentLeftMargin" format="dimension"/>
<attr name="diuMsgRContentBottomMargin" format="dimension"/>

<attr name="diuMsgRContentTextSize" format="dimension"/>
<attr name="diuMsgRContentTextColor" format="reference"/>
<attr name="diuMsgRContentTextBackground" format="reference"/>
<attr name="diuMsgRContentTextPadding" format="dimension"/>
<attr name="diuMsgRContentTextEms" format="integer"/>
<attr name="diuMsgRContentImageSize" format="dimension"/>
<attr name="diuMsgRContentImagePlaceHolder" format="reference"/>
<attr name="diuMsgRContentImageCornerRadius" format="float"/>

<attr name="diuMsgSysContentWidth" format="dimension|integer"/>
<attr name="diuMsgSysContentHeight" format="dimension|integer"/>
<attr name="diuMsgSysContentMinHeight" format="dimension"/>
<attr name="diuMsgSysContentBackground" format="reference"/>
<attr name="diuMsgSysContentTopMargin" format="dimension"/>
<attr name="diuMsgSysContentRightMargin" format="dimension"/>
<attr name="diuMsgSysContentLeftMargin" format="dimension"/>
<attr name="diuMsgSysContentBottomMargin" format="dimension"/>

<attr name="diuMsgMetaTextColor" format="color"/>
<attr name="diuMsgMetaTextResendColor" format="color"/>
<attr name="diuMsgMetaText" format="string"/>
<attr name="diuMsgMetaTextSize" format="dimension"/>
<attr name="diuMsgMetaTextTopMargin" format="dimension"/>
<attr name="diuMsgMetaTextBottomMargin" format="dimension"/>
<attr name="diuMsgMetaTextLeftMargin" format="dimension"/>
<attr name="diuMsgMetaTextRightMargin" format="dimension"/>

<attr name="diuMsgResendBtnWidth" format="dimension|integer"/>
<attr name="diuMsgResendBtnHeight" format="dimension|integer"/>
<attr name="diuMsgResendBtnTopMargin" format="dimension"/>
<attr name="diuMsgResendBtnBottomMargin" format="dimension"/>
<attr name="diuMsgResendBtnLeftMargin" format="dimension"/>
<attr name="diuMsgResendBtnRightMargin" format="dimension"/>
<attr name="diuMsgResendBtnBackground" format="reference"/>
<attr name="diuMsgResendBtnText" format="string"/>
<attr name="diuMsgCreateAtFormat" format="string"/>
</declare-styleable>
```


### DiuitMessageContentFactory
To help developers generate the content of the message view more quickly, each DiuitMessage has a DiuitMessageContentFactory, by calling `DiuitMessageContentFactory.getType(DiuitMessage)` you can get the content type of message. You can also generate different types of content by using DiuitMessageContentFactory.

As the following examples,


Generate a ImagView

<image src="./images/DiuitMessageViewImag.png"/>

```Java
// the data of the message should be a link
// the mime of the message must be started with 'image/'
DiuitMessageContentFactory.bindMessage(DiuitMessage).image().rounded(50.0f).load();
```



Generate a WebView

<image src="./images/DiuitMessageViewWeb.png"/>
<image src="./images/DiuitMessageViewWeb1.png"/>

```Java
// the data of the message should be a link which starts with 'http' or 'https'
// the mime of the message must be 'text/plain'
DiuitMessageContentFactory.bindMessage(DiuitMessage).web().load();
```


Generate a fileView

<image src="./images/DiuitMessageViewFile.png"/>

```Java
// the data of the message should be a link
// the mime of the message must be 'application/octet-stream'
// if the meta of the message contains 'name' and 'size' => {"name":"11.png", "size":8300000, ....}
DiuitMessageContentFactory.bindMessage(DiuitMessage).file().load();
```

Generate a videoView

<image src="./images/DiuitMessageViewVideo.jpg"/>

```Java
// the data of the message should be a link
// the mime of the message must be started with 'video/'
// if the meta of the message contains 'name' and 'size' => {"name":"11.png", "size":8300000, ....}
DiuitMessageContentFactory.bindMessage(DiuitMessage).video().load();
```


You can also pass your customized view as the content view

```Java
DiuitMessageContentFactory.customized(View view)
```

When calling `load()`, the function DiuitMessageContentFactory in DiuitMessageView will call `random()` to generate the content of a message view. In the function  `random()`, mime and the data of message decides the types of DiuitMessageContentFactory. There are six main types, `System`, `Text`, `Image `, `Web `, and `Video`. You can also use customized view as the content of message view. For more information about DiuitMessageContentFactory, please refer to [DiuitMessageContentFactory](#DiuitMessageContentFactory)


### Resend
In some cases, such as unstable network environment, sending messages may fail. In DiuitMessagesView, we will show a ResendButton when the messages can't be sent. User can click the resend buttton to resend messages agian.



## DiuitMemberView
DiuitMemberView parses the DiuitUser object. Check whether the user's meta contains `name` and `url`, and use the value of `name` and `url` as the display name and the link for user's profile icon.

> All Attributes of DiuitMemberView

```xml
<declare-styleable name="DiuitUserView">
<attr name="diuItemBackground" format="reference"/>
<attr name="diuItemMinHeight" format="dimension"/>
<attr name="diuItemHeight" format="dimension|integer"/>
<attr name="diuItemWidth" format="dimension|integer"/>

<attr name="diuMemberIconWidth" format="dimension|integer"/>
<attr name="diuMemberIconHeight" format="dimension|integer"/>
<attr name="diuMemberIconTopMargin" format="dimension"/>
<attr name="diuMemberIconBottomMargin" format="dimension"/>
<attr name="diuMemberIconRightMargin" format="dimension"/>
<attr name="diuMemberIconLeftMargin" format="dimension"/>
<attr name="diuMemberIconSrc" format="reference"/>
<attr name="diuMemberIconBackground" format="reference"/>

<attr name="diuMemberTextWidth" format="dimension|integer"/>
<attr name="diuMemberTextHeight" format="dimension|integer"/>
<attr name="diuMemberTextGravity" format="integer"/>
<attr name="diuMemberTextColor" format="color"/>
<attr name="diuMemberText" format="string"/>
<attr name="diuMemberTextSize" format="dimension"/>
<attr name="diuMemberTextTopMargin" format="dimension"/>
<attr name="diuMemberTextBottomMargin" format="dimension"/>
<attr name="diuMemberTextLeftMargin" format="dimension"/>
<attr name="diuMemberTextRightMargin" format="dimension"/>

<attr name="diuActionIconWidth" format="dimension|integer"/>
<attr name="diuActionIconHeight" format="dimension|integer"/>
<attr name="diuActionIconTopMargin" format="dimension"/>
<attr name="diuActionIconBottomMargin" format="dimension"/>
<attr name="diuActionIconRightMargin" format="dimension"/>
<attr name="diuActionIconLeftMargin" format="dimension"/>
<attr name="diuActionIconSrc" format="reference"/>
<attr name="diuActionIconBackground" format="reference"/>
</declare-styleable>
```

You can customize attributes from xml or use Java code

```java
DiuitMemberView diuitMemberView = (DiuitMemberView) this.findViewById(R.id.view);
diuitMemberView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE);
```

You can bind a user object by calling `bindUser()`

```java
diuitMessageView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindUser(DiuitUser);
```

Call `load()` to load your settings.

```java
diuitMessageView.setAttributes(R.style.YOUR_CUSTOMIZED_STYLE).bindUser(DiuitUser).load();
```

# Utils
In Diuit UI Kit, we also define serveral classes for generating UI components more quickly.


## DiuitImageView
DiuitImageView is an extension of ImageView. You can use `diuitImageView.circle()` to make the image view become circular. In the UI Kit, ImageView all extends from DiuitImageView.


## DiuitMessageDownloadTask
To handle file transfer in chat room, use DiuitMessageDownloadTask to download files through Android original component `DownloadManager.Request`


```Java
DiuitMessageDownloadTask(Context ctx, DiuitMessage diuitMessage)
```

DiuitMessageDownloadTask parses the DiuitMessage object by default. Check whether the meta contains `name` and `desc `, `mime `, and use the value of `name`, `desc `, `mime ` as the file name, description and the mimetype of the file.

You can call `setAllowedNetworkTypes()` to decide the type of network when downloading files.

```Java
diuitMessageDownloadTask.setAllowedNetworkTypes()
```

Use `setNotificationVisibility()` to control whether a system notification is posted by the download manager when the download is running or when it is completed.



## DiuitMessagePreviewFactory
In DiuitMessagePreviewFactory, we now provide ImagePreview and WebPreview, which allows developers to create different components through `image()` and `web()`, and then set up corresponding attributes based on the preview objects.
DiuitMessagePreviewFactory目前提供ImagePreview以及WebPreview。


```Java
this.diuitMessagePreviewFactory = (DiuitMessagePreviewFactory) this.findViewById(R.id.diuitPreview);
diuitMessagePreviewFactory.bindMessage(DiuitMessage).image().setAttribute(R.style.YOUR_CUSTOMIZED_STYLE).load();
```

```Java
this.diuitMessagePreviewFactory = (DiuitMessagePreviewFactory) this.findViewById(R.id.diuitPreview);
diuitMessagePreviewFactory.bindMessage(DiuitMessage).web().setAttribute(R.style.YOUR_CUSTOMIZED_STYLE).load();
```

# License

Copyright 2016 duolC, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


# Contact

If you have any technical questions or concerns about this project feel free to reach out to Diuit Support.
