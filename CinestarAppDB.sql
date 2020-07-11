create database Cinestar_RSS
GO
use Cinestar_RSS
GO
---------------------------
-------CREATE TABLES-------
---------------------------
CREATE TABLE AppUser
(
	IDUser int constraint PK_User primary key identity,
	Username nvarchar(300),
	Password nvarchar(300),
	UserRoleId int constraint FK_UserRole foreign key references UserRole(idRole)
)

CREATE TABLE UserRole
(
	IdRole int primary key identity,
	RoleName nvarchar (100)
)

CREATE TABLE Person
(
	IDPerson INT CONSTRAINT PK_Person PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(100),
	LastName NVARCHAR(100)
)

CREATE TABLE Genre
(
	IDGenre INT CONSTRAINT PK_Genres PRIMARY KEY IDENTITY,
	GenreName NVARCHAR(300)
)


create table Role
(
	IDRole int primary key identity,
	Name nvarchar(100)
)

insert into Role values ('Director')
insert into Role values ('Actor')

insert into UserRole values ('Admin')
insert into UserRole values ('User')

insert into AppUser values ('admin', '4357010', 1)

GO

CREATE TABLE Movie
(
	IDMovie INT CONSTRAINT PK_Movie PRIMARY KEY IDENTITY,
	Title NVARCHAR(300),
	Description NVARCHAR(max),
	OriginalTitle NVARCHAR(300),
	PosterPath NVARCHAR(300),
	Link NVARCHAR(300),
	BeginningDate NVARCHAR(100)
) 

GO
create table MovieInvolvement
(
	MovieId int constraint FK_MovieInvolvement_Movie foreign key references Movie(IdMovie),
	PersonId int constraint FK_MovieInvolvement_Person foreign key references Person(IdPerson),
	RoleId int constraint FK_MovieInvolvement_Role foreign key references Role(IdRole)
)


GO

CREATE TABLE MovieGenres
(
	MovieID INT CONSTRAINT FK_MovieGenre_Movie FOREIGN KEY REFERENCES Movie(IDMovie),
	GenreId INT CONSTRAINT FK_MovieGenre_Genre FOREIGN KEY REFERENCES Genre(IDGenre)
)

GO


--------------------------------------------------
----------CREATE PROCEDURES-----------------------
-------------------------------------------------

------CRUD AppUser----
GO

create proc selectUsers
as
begin
	select * from AppUser
end
GO
create proc createUser
@Username nvarchar(300),
@Password nvarchar(300),
@UserRole int
as
begin
	insert into AppUser values (@Username, @Password, @UserRole)
end

GO


create proc userExists
@Username nvarchar(300),
@Password nvarchar(300),
@Exists smallint output
as
begin
	if exists (select * from AppUser au
				where au.Username = @Username and au.Password = @Password)
		select @Exists = au.UserRoleId from AppUser au where au.Username = @Username AND au.Password = @Password
	else
		set @Exists = 0
end


GO


create proc usernameExists
@Username nvarchar(300),
@Exists bit output
as
begin
	if exists (select * from AppUser au
					where au.Username = @Username)
		begin
			set @Exists = 1
		end
	else
		begin
			set @Exists = 0
		end
end
GO

------CRUD MOVIE------

CREATE PROC createMovie
	@Title NVARCHAR(300),
	@Description NVARCHAR(max),
	@OriginalTitle NVARCHAR(300),
	@PosterPath NVARCHAR(300),
	@Link NVARCHAR(300),
	@BeginningDate NVARCHAR(100),
	@ID INT OUTPUT

AS
BEGIN
	INSERT INTO Movie VALUES (@Title, @Description, @OriginalTitle, @PosterPath, @Link, @BeginningDate)
	SET @ID = SCOPE_IDENTITY()
END

GO

CREATE PROC updateMovie
	@ID INT, 
	@Title NVARCHAR(300),
	@Description NVARCHAR(max),
	@OriginalTitle NVARCHAR(300),
	@PosterPath NVARCHAR(300),
	@Link NVARCHAR(300),
	@BeginningDate NVARCHAR(100)
AS
BEGIN
	UPDATE Movie SET
		Movie.Title = @Title,
		Movie.Description = @Description,
		Movie.OriginalTitle = @OriginalTitle,
		Movie.PosterPath = @PosterPath,
		Movie.Link = @Link,
		Movie.BeginningDate = @BeginningDate
	where Movie.IDMovie = @ID
END

GO

CREATE PROC deleteMovie
@ID int
as
begin
	delete from MovieInvolvement
	where MovieId = @ID

	delete from MovieGenres
	where MovieID = @ID

	delete from Movie
	where IDMovie = @ID
end
GO

create proc selectMovie
@ID int
as
begin
	select * from Movie
	where IDMovie = @ID
end

GO
create proc selectMovies
as
begin
	select * from Movie
end

GO

create proc selectMoviesIdByPersonRole
@personId int,
@roleId int
as
begin
	select mi.MovieId from MovieInvolvement as mi
	where mi.PersonId = @personId AND mi.RoleId = @roleId
end


GO

------CRUD PERSON-----

create proc createPerson
@firstName nvarchar(300),
@lastName nvarchar(300),
@PersonID int output
as
begin
	if not exists (select * from Person as p where p.FirstName = @firstName AND p.LastName = @lastName)
	begin
		insert into Person values (@firstName, @lastName)
		set @PersonID = SCOPE_IDENTITY()
	end
	else
	begin 
		set @PersonID = (select p.IDPerson from Person as p where p.FirstName = @firstName AND p.LastName = @lastName)
	end
end

GO

create proc updatePerson
@PersonID int,
@firstName nvarchar(300),
@lastName nvarchar(300)
as
begin
	update Person
	set Person.FirstName = @firstName,
		Person.LastName = @lastName
	where Person.IDPerson = @PersonID
end

GO

create proc deletePerson
@PersonId int
as
begin
	delete from MovieInvolvement where MovieInvolvement.PersonId = @PersonId
	delete from Person where Person.IDPerson = @PersonId
end

GO
create proc selectPerson
@PersonId int
as
begin
	select * from Person where IDPerson = @PersonId
end

GO

create proc selectPersons
as
begin
	select * from Person
end

GO


------CRUD MOVIE ROLE--------
GO

create proc createMovieInvolvement
@MovieId int,
@PersonId int,
@RoleId int
as
begin
	insert into MovieInvolvement values (@MovieId, @PersonId, @RoleId)
end
 GO

create proc deleteMovieInvolvement
@MovieId int,
@PersonId int,
@RoleId int
as
begin
	delete from MovieInvolvement where MovieId = @MovieID AND PersonId = @PersonId AND RoleId = @RoleId
end

GO

create proc deleteMovieInvolvementsByMovieId
@MovieId int
as 
begin
	delete from MovieInvolvement where MovieId = @MovieId
end

GO
create proc deleteMovieInvolvementsByPersonId
@PersonId int
as 
begin
	delete from MovieInvolvement where PersonId = @PersonId
end

GO

create proc selectMovieInvolvementsByRoleId
@movieId int,
@roleId int
as
begin
	select * from Person
	where IDPerson in
		(select PersonId from MovieInvolvement as mi
		where mi.MovieId = @movieId and mi.RoleId = @roleId)
end

GO
create proc selectInvolvements
as
begin
	select * from Person as p
	inner join MovieInvolvement as mi
	on p.IDPerson = mi.PersonId
end

----------SELECT GENRES OF THE MOVIE------------
GO

create proc createGenre
@GenreName nvarchar(100),
@IDGenre int output
as
begin
	insert into Genre values (@GenreName)
	set @IDGenre = SCOPE_IDENTITY()
end

GO
create proc createMovieGenre
@movieId int,
@genreId int
as
begin
	insert into MovieGenres values (@movieId, @genreId)
end

GO

create proc selectMovieGenres
@MovieID int
as
begin
	select *
	from Genre as g
	where g.IDGenre in
		(select mg.GenreId
		from MovieGenres as mg
		where mg.MovieID = @MovieID)
end

GO

create proc deleteMovieGenres
@MovieId int
as
begin
	delete from MovieGenres where MovieGenres.MovieID = @MovieId
end

GO

create proc selectGenres
as 
begin
	select * from Genre
end

GO

create proc deleteAllData
as
begin
	delete from MovieGenres
	delete from MovieInvolvement
	delete from MovieGenres
	delete from Person
	delete from Genre
	delete from Movie
	
	DBCC CHECKIDENT ('Person', RESEED, 0);
	DBCC CHECKIDENT ('Genre', RESEED, 0);
	DBCC CHECKIDENT ('Movie', RESEED, 0);
end