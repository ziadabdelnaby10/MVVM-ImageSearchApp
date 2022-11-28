# MVVM Image Search
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
a simple app to practice on some new topics created by [Coding In Flow](https://www.youtube.com/playlist?list=PLrnPJCHvNZuC_pEfFlZuTmjlY4T3DTtED) like:-
- Retrofit
- Glide (Coil Was Bad)
- RecyclerView with ListAdapter and Page Adapter
- Paging Library 3
- Coroutines (Defered for api)
- MVVM
- Dependency Injection Using Dagger Hilt
- Two-Way DataBinding & BindingAdapters
- Navigation Component
- Single Activity Architecture

##Demo
![Demo](https://drive.google.com/file/d/13CsIuNzWV_TqXtv0YhqTvE4G1Ug101ut/view?usp=sharing)

### Dependencies

```
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    def paging_version = "3.1.1"
    def nav_version = "2.5.3"
    def retrofit_version = "2.9.0"
    def dagger_version = "2.44"
    def lifecycle_version = "2.6.0-alpha03"
    def lottieVersion = "3.4.0"
    def moshiVersion = "1.13.0"


    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'com.google.android.material:material:1.7.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    //Paging3
    implementation "androidx.paging:paging-runtime:$paging_version"

    //Kotlin Navigation Component
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

    //Retrofit
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-moshi:$retrofit_version"
    implementation "com.squareup.moshi:moshi-kotlin:$moshiVersion"

    //Dagger Hilt
    implementation "com.google.dagger:hilt-android:$dagger_version"
    kapt "com.google.dagger:hilt-compiler:$dagger_version"

    //Coil
    implementation "io.coil-kt:coil:2.2.2"

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.14.2'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.14.2'
    implementation 'jp.wasabeef:glide-transformations:4.3.0'

    //Circular Image View
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    //Shimmer
    implementation 'com.facebook.shimmer:shimmer:0.5.0'

    //Lifecycle
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    //Lottie
    implementation "com.airbnb.android:lottie:$lottieVersion"
```

### Plugins

```
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'androidx.navigation.safeargs.kotlin'
    id 'kotlin-parcelize'
    id 'kotlin-kapt'
    id 'com.google.dagger.hilt.android'
```

# Paging library 3 Overview

## Benefits

The Paging library includes the following features:

a) In-memory caching for your paged data. This ensures that your app uses system resources efficiently while working with paged data.

b) Built-in request deduplication, ensuring that your app uses network bandwidth and system resources efficiently.

c) Configurable RecyclerView adapters that automatically request data as the user scrolls toward the end of the loaded data.

d) First-class support for Kotlin coroutines and Flow, as well as LiveData and RxJava.

e) Built-in support for error handling, including refresh and retry capabilities.


## Paging Library Components
`PagingData` - a container for paginated data. Each refresh of data will have a separate corresponding `PagingData`.

`PagingSource` - a `PagingSource` is the base class for loading snapshots of data into a stream of `PagingData`.

`Pager.flow` - builds a `Flow<PagingData>`, based on a `PagingConfig` and a function that defines how to construct the implemented `PagingSource`.

`PagingDataAdapter` - a `RecyclerView.Adapter` that presents `PagingData` in a `RecyclerView`. The `PagingDataAdapter` can be connected to a Kotlin `Flow`, a `LiveData`, an `RxJava Flowable`, or an `RxJava Observable`. The `PagingDataAdapter` listens to internal PagingData loading events as pages are loaded and uses `DiffUtil` on a background thread to compute fine-grained updates as updated content is received in the form of new `PagingData` objects.

`RemoteMediator` - helps implement pagination from network and database.

#Navigation Component Graph
![navigation](https://user-images.githubusercontent.com/38537133/203988917-7747aeca-b1cb-43a0-8d37-ca90792d0c7b.png)
