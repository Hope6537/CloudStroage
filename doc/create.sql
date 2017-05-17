CREATE TABLE `member` (
  `memberId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `itemtype` (
  `typeId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `typeName` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hander` (
  `handerId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `memberId` int(11) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `folder` varchar(255) DEFAULT NULL,
  `fullPath` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updateDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`handerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `iteminfo` (
  `itemId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `absolutePath` varchar(255) DEFAULT NULL,
  `serverPath` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sha1` varchar(255) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;