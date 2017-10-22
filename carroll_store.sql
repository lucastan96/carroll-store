-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 07, 2016 at 12:55 PM
-- Server version: 10.1.8-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carroll_store`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddNewProduct` (IN `id` INT, IN `name` VARCHAR(50), IN `price` FLOAT, IN `stock` INT)  BEGIN
	INSERT INTO product(categoryID,productName,unitPrice,inStock) VALUES(id,name,price,stock);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteOrder` (IN `id` INT)  BEGIN
	DELETE FROM orders WHERE orderID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteProduct` (IN `id` INT)  BEGIN
	DELETE FROM product WHERE productID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ShowAllProduct` ()  BEGIN
	SELECT * FROM product;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ShowOrderOfCustomer` (IN `id` INT)  BEGIN
	SELECT o.orderID,o.orderDate,sum(od.quantity*p.unitPrice) as totalPrice FROM orders as o,orderdetail as od,product as p WHERE o.customerID = id AND o.orderID = od.orderID AND od.productID = p.productID GROUP BY o.orderID;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateCustomerAddress` (IN `address` VARCHAR(50), IN `id` INT)  BEGIN
	UPDATE customer SET customerAddress = address WHERE customerID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdatePrice` (IN `price` FLOAT, IN `id` INT)  BEGIN
	UPDATE product SET unitPrice = price WHERE productID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateStaffAddress` (IN `address` VARCHAR(50), IN `id` INT)  BEGIN
	UPDATE staff SET staffAddress = address WHERE staffID = id;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `categoryID` int(11) NOT NULL,
  `categoryName` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerID` int(11) NOT NULL,
  `customerName` varchar(20) NOT NULL,
  `customerEmail` varchar(50) DEFAULT NULL,
  `customerAddress` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `orderID` int(11) NOT NULL,
  `orderDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `quantity` int(11) DEFAULT NULL,
  `productID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `orderdetail`
--
DELIMITER $$
CREATE TRIGGER `updateProduct` AFTER DELETE ON `orderdetail` FOR EACH ROW BEGIN
	UPDATE product
    SET product.inStock = product.inStock + OLD.quantity
    WHERE product.productID = OLD.productID;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateStock` AFTER INSERT ON `orderdetail` FOR EACH ROW BEGIN
	UPDATE product
    SET product.inStock = product.inStock - NEW.quantity
    WHERE product.productID = NEW.productID;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `orderID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `staffID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productID` int(11) NOT NULL,
  `categoryID` int(11) NOT NULL,
  `productName` varchar(50) DEFAULT NULL,
  `unitPrice` int(11) DEFAULT NULL,
  `inStock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffID` int(11) NOT NULL,
  `staffName` varchar(20) DEFAULT NULL,
  `staffEmail` varchar(50) DEFAULT NULL,
  `staffAddress` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_orderdetail`
--
CREATE TABLE `v_orderdetail` (
`productName` varchar(50)
,`quantity` int(11)
,`totalPrice` bigint(21)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_product_instock`
--
CREATE TABLE `v_product_instock` (
`productName` varchar(50)
,`unitPrice` int(11)
,`inStock` int(11)
);

-- --------------------------------------------------------

--
-- Structure for view `v_orderdetail`
--
DROP TABLE IF EXISTS `v_orderdetail`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_orderdetail`  AS  select `product`.`productName` AS `productName`,`orderdetail`.`quantity` AS `quantity`,(`product`.`unitPrice` * `orderdetail`.`quantity`) AS `totalPrice` from (`product` join `orderdetail`) where (`product`.`productID` = `orderdetail`.`productID`) ;

-- --------------------------------------------------------

--
-- Structure for view `v_product_instock`
--
DROP TABLE IF EXISTS `v_product_instock`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_product_instock`  AS  select `product`.`productName` AS `productName`,`product`.`unitPrice` AS `unitPrice`,`product`.`inStock` AS `inStock` from `product` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`categoryID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerID`);

--
-- Indexes for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`orderID`,`productID`),
  ADD KEY `productID` (`productID`),
  ADD KEY `orderID` (`orderID`),
  ADD KEY `indexF` (`orderID`,`quantity`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `customerID` (`customerID`),
  ADD KEY `staffID` (`staffID`),
  ADD KEY `indexB` (`orderID`,`customerID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`),
  ADD KEY `categoryID` (`categoryID`),
  ADD KEY `index_a` (`productID`,`productName`,`unitPrice`,`inStock`),
  ADD KEY `indexA` (`productID`,`productName`,`unitPrice`,`inStock`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `categoryID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customerID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staffID` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD CONSTRAINT `orderdetail_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  ADD CONSTRAINT `orderdetail_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
