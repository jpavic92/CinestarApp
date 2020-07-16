## CinestarApp
### About
CinnestarApp is Java application which fetches and presents data about movies announcements from Cinestar/RSS in Croatia. You can view, delete and edit existing data, or you can add a new movie announcement with all belonging info.
### Starting
#### Create database
 * Run *CinestarAppDB.slq* script to create database
#### Add libraries
Project uses some external libraries which have to be added to project:
* [Microsoft JDBC Driver for SQL Server 7.0.0](https://jar-download.com/download-handling.php)
  - has to be added to "CinestarApp" Application
* [Apache Commons Validator](http://commons.apache.org/proper/commons-validator/)
  - has to be added both to "Utils" Class Library and "CinestarApp" Application
