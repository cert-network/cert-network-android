# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/dd/Work/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes Signature

-ignorewarnings

-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
    BottomNavigationMenuView menuView;
}
-keepclassmembers class com.google.android.material.bottomnavigation.BottomNavigationMenuView {
    boolean mShiftingMode;
    BottomNavigationMenuView menuView;
}
-dontwarn com.google.android.gms.**



##================= Gson  ========================================================================##
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#network dao model of GSON
-keep class **.model.** { <fields>; }
-keep class **.response.** { <fields>; }
-keep class **.request.** { <fields>; }
-keep class **.data.** { <fields>; }
#-keepclassmembers class **.model.** {*;}
#-keepclassmembers class **.response.**{*;}
#-keepclassmembers class **.request.**{*;}
#-keepclassmembers class **.data.**{*;}

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Proguard configurations for common Android libraries:
# https://github.com/krschultz/android-proguard-snippets