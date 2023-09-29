# Wastewarrior Android Application

## Project Overview

Wastewarrior is an Android application aimed at reducing food waste by connecting users with restaurants that offer 'surprise bags' of food that would otherwise go to waste.

## Features

- Explore restaurants on Google Maps
- View and purchase 'surprise bags' from different restaurants
- Manage your favorite restaurants
- Access settings and notifications

## Prerequisites

- Android Studio
- Kotlin
- Google Maps API Key

## How to Run

1. Clone the repository:
    ```
    git clone https://github.com/Aag2102/food_waste_app_kotlin
    ```
2. Open Android Studio and import the `food_waste_app_kotlin/wastewarrior` directory.
3. Place your Google Maps API key in the `res/values/strings.xml` file.
4. Build and run the app on an emulator or an Android device.

## Directory Structure

```
wastewarrior/
│
├── models/  # Data classes that represent different entities
│
├── user/
│   ├── main/
│   │   ├── dashboard/
│   │   │   ├── DashboardFragment  # Explore page showing restaurants on Google Maps
│   │   │   ├── DashboardViewModel  # (Not in use currently)
│   │   │   └── FavouritesAdapter   # RecyclerView adapter for user favorites
│   │   │
│   │   ├── home/
│   │   │   ├── HomeFragment       # List of all surprise bags from restaurants
│   │   │   └── HomeViewModel      # (Not in use currently)
│   │   │
│   │   └── notifications/
│   │       ├── NotificationsFragment  # Settings page
│   │       └── NotificationsViewModel # (Not in use currently)
│   │
│   ├── RestaurantAdapter            # RecyclerView adapter for restaurants
│   ├── SurpriseBagesClientAdapter   # RecyclerView adapter for surprise bags
│   ├── LoginActivity                # User login functionality
│   └── MainActivity                 # App's main entry point, bottom navigation
│
└── ... (Other directories and files)
```

## Contributing

Feel free to open issues and pull requests!
