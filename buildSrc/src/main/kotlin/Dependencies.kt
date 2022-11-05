object Dependencies {

    const val androidCore = "androidx.core:core-ktx:${Versions.androidCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"

    const val baseArch = "com.internal.icell.basearch:basearch:${Versions.baseArch}"
    const val dexter = "com.karumi:dexter:${Versions.dexter}"

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinPlugin}"
    const val kotlinCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinDependency}"
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinDependency}"
    const val kotlinSerializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"

    const val playServicesLocation =
        "com.google.android.gms:play-services-location:${Versions.PlayServices.location}"
    const val playServicesMap =
        "com.google.android.gms:play-services-maps:${Versions.PlayServices.maps}"
    const val firebaseMessaging =
        "com.google.firebase:firebase-messaging-ktx:${Versions.firebaseMessaging}"

    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation.fragment}"
    const val navigationUIKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.Navigation.UI}"

    const val hiltAndroid =
        "com.google.dagger:hilt-android:${Versions.Hilt.core}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.Hilt.core}"
    const val hiltLifecycleViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Hilt.lifecycleViewModel}"

    const val hiltCore = "com.google.dagger:hilt-core:${Versions.Hilt.core}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.Hilt.core}"

    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    const val jodaTime = "net.danlew:android.joda:${Versions.joda}"

    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"

    const val junit = "junit:junit:${Versions.junit}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitKotlinSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinSerialization}"

    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"

    const val sentry = "io.sentry:sentry-android:${Versions.sentry}"

    const val dataStorePreferences =
        "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideOkHttpIntegration =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    const val dotsIndicator = "com.tbuonomo:dotsindicator:${Versions.dotsIndicator}"
    const val imagePicker = "com.github.dhaval2404:imagepicker:${Versions.imagePicker}"

    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val pagingCompose = "androidx.paging:paging-compose:${Versions.pagingCompose}"

    const val mapLibre = "org.maplibre.gl:android-sdk:${Versions.mapLibre}"

    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUITooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUIToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val lifecycleViewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewModelCompose}"

    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    const val navigationAnimationCompose =
        "com.google.accompanist:accompanist-navigation-animation:${Versions.navigationAnimationCompose}"

    const val swipeRefreshCompose =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeRefreshCompose}"
    const val chuckerLogger =
        "com.github.chuckerteam.chucker:library:${Versions.chuckerLogger}"
    const val chuckerLoggerNoOp =
        "com.github.chuckerteam.chucker:library-no-op:${Versions.chuckerLogger}"
    const val pagerIndicator = "com.google.accompanist:accompanist-pager-indicators:${Versions.pager}"
    const val pager = "com.google.accompanist:accompanist-pager:${Versions.pager}"
}
