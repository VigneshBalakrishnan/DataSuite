TRUNCATE TABLE TBL_DMS_USERS
TRUNCATE TABLE TBL_DFX_Groups
TRUNCATE TABLE TBL_DFX_User_Groups

SET IDENTITY_INSERT [dbo].[TBL_DMS_USERS] ON 

INSERT [dbo].[TBL_DMS_USERS] ([User_Id], [FirstName], [LastName], [StaffName], [Gender], [Logonid], [Email], [Title], [Jurisdiction], [DepartmentName], [LineManagerId], [TelephoneNumber], [IsLineManager], [DomainUser], [DirectorID], [IsDirector], [LineManagerName], [TotalLeaveCount], [TakenLeaveCount], [EmployeeImagePath], [Office], [Division], [SubDepartment], [LaserficheGroup], [Domain], [Folder], [Flag], [DeletedRecord], [BalanceLeaveCount], [ExecutionId]) VALUES (1, N'Administrator', N'DFX', N'Administrator', N'', N'dfxadmin', N'sample@datafabricx.com', N'', N'Jersey', N'Laserfiche', 10, N'', 0, N'Datafabricx\dfxadmin', 0, 0, N'Sample', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL)

SET IDENTITY_INSERT [dbo].[TBL_DMS_USERS] OFF