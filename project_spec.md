# **Media Tracker**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description

**App that would allow users to visually track and organize their media. It would display each item in a grid-like card style layout with title, status, and genre of the media**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->

**Description:** App that would allow a user to visually track and organize their media. It would display each item in a grid-like card style layout with title, status, and genre of the media.
**Category:** Lifestyle
**Mobile:** Users can easily access the app on their phone and store any media list information on the device.
**Story:** Allow users to track their media and the status of what's already been seen and what hasn't in a more visually appealing manner than just reading from a list/notes by using a grid-like layout to display media and their corresponding images, titles, status and genre.
**Market:** Any user who wants to track what media they have already consumed or have yet to start or complete.
**Habit:** Users check in to update what they have watched/read or want to watch/read in the future.
**Scope:** Main features can be completed within the deadline.

## Product Spec

### 1. User Features (Required and Optional)

Required Features:
- **Users can add/mark consumed media**
- **Display of user media in a grid-like layout**
- **Grid Squares display different media and their information, including their titles, status, and genre**

Stretch Features:
- **Users can search for media**
- **Store user data**
- **Media can be filtered**

### 2. Chosen API(s)

- **TMDB API**
    - *Lists out media information for display (title, genre, image)*
    - *Allows users to search and browse through media*
    - *Users can add/store media to "watched" list*
- **Google Books**
    - *(Same user features, but with books)*

### 3. User Interaction

Required Feature

***Listing Media***
>- **List Movies:** The user will see all movies added in watch list
   >   - => **User opens app, and user's "watched" movies display on landing**
>
>- **List Books** The user will see all books added in reading list
   >   - => **User switches to "books" tab**
>   - => **User's "read" books display on landing**

***Searching Media***
>- **Get Movie(s) by Title:** User can search for a movie by its title.
   >   - => **User inputs movie title into TextField**
>   - => **API fetches movies containing specified keywords from user input**
>   - => **Displays movies back to client**
>
>- **Get Book(s) by Title:** User can search for a book by its title
   >   - => **User in 'books' tab**
>   - => **API fetches books containing specified keywords from user input**
>   - => **Displays books back to client**
>
>- **Filter By:** User can apply filters to enhance search
   >   - => **User applies filter category**
>   - => **API fetches media where filter is true**
>   - => **Displays filtered media back to client**

***Adding Media***
>- **Add Movie:** User can add secified movie to watch list
   >   - => **User taps "add" buttonView**
>   - => **onClick function/request to add movie to "watched" list**
>   - => **Popup toast prompts user that movie was added**
>- **Add Book:** User can add specified book to reading list
   >   - => **User taps "add" buttonView**
>   - => **onClick function/request to add book to "read" list**
>   - => **Popup toast prompts user that book was added**

## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->
![mediatrackerwireframe](https://hackmd.io/_uploads/BJFGemxOeg.jpg)

### [BONUS] Digital Wireframes & Mockups
![digitalwireframe](https://hackmd.io/_uploads/S1XIl7e_el.jpg)

### [BONUS] Interactive Prototype

## Build Notes

Gif displaying working RecyclerView for movies
<img src='https://i.imgur.com/FETz1WH.gif' title='Build Process' width='' alt='Build Process of recyclerview' />

Gif of user interface
<img src='https://i.imgur.com/KEWEN03.gif' title='User Interface' width='' alt='User interface of media tracker app' />

## License

Copyright **yyyy** **your name**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.