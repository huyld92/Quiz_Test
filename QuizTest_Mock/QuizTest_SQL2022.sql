USE [master]
GO
/****** Object:  Database [Quiz_Test]    Script Date: 7/30/2023 3:24:39 PM ******/
CREATE DATABASE [Quiz_Test]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Quiz_Test', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Quiz_Test.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Quiz_Test_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Quiz_Test_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Quiz_Test] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Quiz_Test].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Quiz_Test] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Quiz_Test] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Quiz_Test] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Quiz_Test] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Quiz_Test] SET ARITHABORT OFF 
GO
ALTER DATABASE [Quiz_Test] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Quiz_Test] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Quiz_Test] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Quiz_Test] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Quiz_Test] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Quiz_Test] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Quiz_Test] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Quiz_Test] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Quiz_Test] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Quiz_Test] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Quiz_Test] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Quiz_Test] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Quiz_Test] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Quiz_Test] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Quiz_Test] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Quiz_Test] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Quiz_Test] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Quiz_Test] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Quiz_Test] SET  MULTI_USER 
GO
ALTER DATABASE [Quiz_Test] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Quiz_Test] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Quiz_Test] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Quiz_Test] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Quiz_Test] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Quiz_Test] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [Quiz_Test] SET QUERY_STORE = OFF
GO
USE [Quiz_Test]
GO
/****** Object:  Table [dbo].[Persistent_Logins]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Persistent_Logins](
	[series] [varchar](64) NOT NULL,
	[username] [varchar](64) NOT NULL,
	[token] [varchar](64) NOT NULL,
	[last_used] [datetime] NOT NULL,
 CONSTRAINT [PK_Persistent_Logins] PRIMARY KEY CLUSTERED 
(
	[series] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblAnswer]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblAnswer](
	[answerID] [int] IDENTITY(1,1) NOT NULL,
	[questionID] [int] NOT NULL,
	[answerText] [nvarchar](500) NOT NULL,
	[isCorrect] [bit] NOT NULL,
 CONSTRAINT [PK_tblAnswer] PRIMARY KEY CLUSTERED 
(
	[answerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblQuestion]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblQuestion](
	[questionID] [int] IDENTITY(1,1) NOT NULL,
	[quizID] [int] NOT NULL,
	[questionText] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_tblQuestion] PRIMARY KEY CLUSTERED 
(
	[questionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblQuiz]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblQuiz](
	[quizID] [int] IDENTITY(1,1) NOT NULL,
	[userID] [int] NOT NULL,
	[topicID] [int] NOT NULL,
	[quizTitle] [nvarchar](50) NOT NULL,
	[totalTime] [int] NOT NULL,
	[totalQuestion] [int] NOT NULL,
 CONSTRAINT [PK_tblQuiz] PRIMARY KEY CLUSTERED 
(
	[quizID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblResult]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblResult](
	[resultID] [int] IDENTITY(1,1) NOT NULL,
	[userID] [int] NOT NULL,
	[testID] [int] NOT NULL,
	[countCorrect] [int] NOT NULL,
	[joinTime] [datetime] NOT NULL,
	[submitTime] [datetime] NULL,
	[totaMark] [int] NOT NULL,
	[statusResult] [bit] NOT NULL,
 CONSTRAINT [PK_tblQuizResult] PRIMARY KEY CLUSTERED 
(
	[resultID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblResult_Answer]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblResult_Answer](
	[resultID] [int] NOT NULL,
	[answerID] [int] NOT NULL,
 CONSTRAINT [PK_Result_Answer] PRIMARY KEY CLUSTERED 
(
	[resultID] ASC,
	[answerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRoles]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoles](
	[roleID] [int] IDENTITY(1,1) NOT NULL,
	[roleName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblRoles] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_roleName] UNIQUE NONCLUSTERED 
(
	[roleName] DESC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblTest]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblTest](
	[testID] [int] IDENTITY(1,1) NOT NULL,
	[userID] [int] NOT NULL,
	[quizID] [int] NOT NULL,
	[testTitle] [nvarchar](50) NOT NULL,
	[startTime] [datetime] NOT NULL,
	[endTime] [datetime] NOT NULL,
	[joinCode] [char](8) NOT NULL,
	[statusTest]  AS (case when [endTime]>=getdate() then 'True' else 'False' end),
	[numOfParticipants] [int] NOT NULL,
 CONSTRAINT [PK_tblTest] PRIMARY KEY CLUSTERED 
(
	[testID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblTopic]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblTopic](
	[topicID] [int] IDENTITY(1,1) NOT NULL,
	[topicName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblTopic] PRIMARY KEY CLUSTERED 
(
	[topicID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_topicName] UNIQUE NONCLUSTERED 
(
	[topicName] DESC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblUser]    Script Date: 7/30/2023 3:24:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblUser](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[userName] [nvarchar](50) NOT NULL,
	[password] [varchar](100) NOT NULL,
	[fullName] [nvarchar](100) NOT NULL,
	[email] [nvarchar](50) NULL,
	[roleID] [int] NOT NULL,
	[enabled] [bit] NOT NULL,
 CONSTRAINT [PK_tblUser] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_email] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_userName] UNIQUE NONCLUSTERED 
(
	[userName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tblUser] ADD  CONSTRAINT [DF_tblUser_status]  DEFAULT ((0)) FOR [enabled]
GO
ALTER TABLE [dbo].[tblAnswer]  WITH CHECK ADD  CONSTRAINT [FK_tblAnswer_tblQuestion] FOREIGN KEY([questionID])
REFERENCES [dbo].[tblQuestion] ([questionID])
GO
ALTER TABLE [dbo].[tblAnswer] CHECK CONSTRAINT [FK_tblAnswer_tblQuestion]
GO
ALTER TABLE [dbo].[tblQuestion]  WITH CHECK ADD  CONSTRAINT [FK_tblQuestion_tblQuiz] FOREIGN KEY([quizID])
REFERENCES [dbo].[tblQuiz] ([quizID])
GO
ALTER TABLE [dbo].[tblQuestion] CHECK CONSTRAINT [FK_tblQuestion_tblQuiz]
GO
ALTER TABLE [dbo].[tblQuiz]  WITH CHECK ADD  CONSTRAINT [FK_tblQuiz_tblGroup] FOREIGN KEY([topicID])
REFERENCES [dbo].[tblTopic] ([topicID])
GO
ALTER TABLE [dbo].[tblQuiz] CHECK CONSTRAINT [FK_tblQuiz_tblGroup]
GO
ALTER TABLE [dbo].[tblQuiz]  WITH CHECK ADD  CONSTRAINT [FK_tblQuiz_tblUser] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUser] ([userID])
GO
ALTER TABLE [dbo].[tblQuiz] CHECK CONSTRAINT [FK_tblQuiz_tblUser]
GO
ALTER TABLE [dbo].[tblResult]  WITH CHECK ADD  CONSTRAINT [FK_tblQuizResult_tblTest] FOREIGN KEY([testID])
REFERENCES [dbo].[tblTest] ([testID])
GO
ALTER TABLE [dbo].[tblResult] CHECK CONSTRAINT [FK_tblQuizResult_tblTest]
GO
ALTER TABLE [dbo].[tblResult]  WITH CHECK ADD  CONSTRAINT [FK_tblQuizResult_tblUser] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUser] ([userID])
GO
ALTER TABLE [dbo].[tblResult] CHECK CONSTRAINT [FK_tblQuizResult_tblUser]
GO
ALTER TABLE [dbo].[tblResult_Answer]  WITH CHECK ADD  CONSTRAINT [FK_Result_Answer_tblAnswer] FOREIGN KEY([answerID])
REFERENCES [dbo].[tblAnswer] ([answerID])
GO
ALTER TABLE [dbo].[tblResult_Answer] CHECK CONSTRAINT [FK_Result_Answer_tblAnswer]
GO
ALTER TABLE [dbo].[tblResult_Answer]  WITH CHECK ADD  CONSTRAINT [FK_Result_Answer_tblResult] FOREIGN KEY([resultID])
REFERENCES [dbo].[tblResult] ([resultID])
GO
ALTER TABLE [dbo].[tblResult_Answer] CHECK CONSTRAINT [FK_Result_Answer_tblResult]
GO
ALTER TABLE [dbo].[tblTest]  WITH CHECK ADD  CONSTRAINT [FK_tblTest_tblQuiz] FOREIGN KEY([quizID])
REFERENCES [dbo].[tblQuiz] ([quizID])
GO
ALTER TABLE [dbo].[tblTest] CHECK CONSTRAINT [FK_tblTest_tblQuiz]
GO
ALTER TABLE [dbo].[tblTest]  WITH CHECK ADD  CONSTRAINT [FK_tblTest_tblUser] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUser] ([userID])
GO
ALTER TABLE [dbo].[tblTest] CHECK CONSTRAINT [FK_tblTest_tblUser]
GO
ALTER TABLE [dbo].[tblUser]  WITH CHECK ADD  CONSTRAINT [FK_tblUser_tblRoles] FOREIGN KEY([roleID])
REFERENCES [dbo].[tblRoles] ([roleID])
GO
ALTER TABLE [dbo].[tblUser] CHECK CONSTRAINT [FK_tblUser_tblRoles]
GO
USE [master]
GO
ALTER DATABASE [Quiz_Test] SET  READ_WRITE 
GO
