CREATE TABLE 'ifact'.`leakage` (
  `leakageID` int(11) GENERATED ALWAYS AS (100) STORED NOT NULL,
  `EmpID` varchar(45) NOT NULL,
  `WorkerID` varchar(45) NOT NULL,
  `LeakageHrs` varchar(45) DEFAULT NULL,
  `LeakageReason` varchar(45) DEFAULT NULL,
  `LeakageRevenue` varchar(45) DEFAULT NULL,
  `LastUpdatedDate` date DEFAULT NULL,
  `LastUpdatedUser` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`leakageID`),
  KEY `fk_emp_id` (`EmpID`),
  KEY `fk_worker_id` (`WorkerID`),
  CONSTRAINT `fk_emp_id` FOREIGN KEY (`EmpID`) REFERENCES `pwb` (`empid`) ON DELETE CASCADE,
  CONSTRAINT `fk_worker_id` FOREIGN KEY (`WorkerID`) REFERENCES `pwb` (`empid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE 'ifact'.`pwb` (
  `EmpID` varchar(45) NOT NULL,
  `WorkerID` varchar(45) NOT NULL,
  `GDCEID` varchar(45) DEFAULT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `WONNumber` varchar(45) DEFAULT NULL,
  `WONName` varchar(45) DEFAULT NULL,
  `WONType` varchar(45) DEFAULT NULL,
  `WorkCountry` varchar(45) DEFAULT NULL,
  `WorkLocation` varchar(45) DEFAULT NULL,
  `SOWWorkerRole` varchar(45) DEFAULT NULL,
  `ProjectType` varchar(45) DEFAULT NULL,
  `DM` varchar(45) DEFAULT NULL,
  `DP` varchar(45) DEFAULT NULL,
  `CP` varchar(45) DEFAULT NULL,
  `CurrentBillRate` varchar(45) DEFAULT NULL,
  `WorkerStartDate` date DEFAULT NULL,
  `WorkerEndDate` date DEFAULT NULL,
  `Month1Max` varchar(45) DEFAULT NULL,
  `Month2Max` varchar(45) DEFAULT NULL,
  `Month3Max` varchar(45) DEFAULT NULL,
  `Month1Act` varchar(45) DEFAULT NULL,
  `Month2Act` varchar(45) DEFAULT NULL,
  `Month3Act` varchar(45) DEFAULT NULL,
  `Month1Diff` varchar(45) DEFAULT NULL,
  `Month2Diff` varchar(45) DEFAULT NULL,
  `Month3Diff` varchar(45) DEFAULT NULL,
  `LastUpdatedDate` date DEFAULT NULL,
  `LastUpdatedUser` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`EmpID`,`WorkerID`),
  KEY `DP` (`DP`),
  KEY `DM` (`DM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE 'ifact'.`users` (
  `user` varchar(45) NOT NULL,
  `fullname` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `project` varchar(45) DEFAULT NULL,
  `enabled` varchar(45) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `dateofjoining` date DEFAULT NULL,
  PRIMARY KEY (`user`),
  KEY `enabled` (`enabled`),
  KEY `email` (`email`),
  KEY `token` (`dateofjoining`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci