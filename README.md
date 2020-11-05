Utilized: Kotlin, Retrofit, RxJava2, Room, Dagger Hilt, Navigation Components, Material Design, Glide, CircleImageView
Architecture: MVVM

**To be able to use the app please provide a Github API token in Constants file in package 'com.example.githubproject.util' for GITHUB_API_TOKEN.**

Additional features not mentioned explicitly in spec: 

    - Ability to unfavorite a repository

    - Displays progressbar when loading repository data, a retry button when the networking call was unsuccessful

    - Screen data survives process death through the use of SavedStateHandle in ViewModel

    - Will check to see if the repositories fetched from the network have already been favorited or not
