-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS `itss`;

-- Sử dụng cơ sở dữ liệu vừa tạo
USE itss;

-- Tạo bảng building
CREATE TABLE building (
    building_id INT PRIMARY KEY,
    name VARCHAR(40),
    address VARCHAR(40),
    total_apartment INT
);

-- Tạo bảng apartment
CREATE TABLE apartment (
    apartment_id INT PRIMARY KEY,
    building_id INT,
    apartment_number INT,
    size INT,
    rent INT,
    FOREIGN KEY (building_id) REFERENCES building(building_id)
);

-- Tạo bảng tenant
CREATE TABLE tenant (
    tenant_id INT PRIMARY KEY,
    apartment_id INT,
    name VARCHAR(40),
    contact VARCHAR(40),
    status VARCHAR(40),
    FOREIGN KEY (apartment_id) REFERENCES apartment(apartment_id)
);

-- Tạo bảng contract
CREATE TABLE contract (
    contract_id INT PRIMARY KEY,
    tenant_id INT,
    start_date DATE,
    end_date DATE,
    notes VARCHAR(40),
    payment_amount INT,
    FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
);

-- Tạo bảng payment
CREATE TABLE payment (
    payment_id INT PRIMARY KEY,
    contract_id INT,
    payment_date DATE,
    amount INT,
    FOREIGN KEY (contract_id) REFERENCES contract(contract_id)
);

-- Tạo bảng user
CREATE TABLE user (
    user_id INT PRIMARY KEY,
    username VARCHAR(40),
    password VARCHAR(40),
    role VARCHAR(40)
);

-- Tạo bảng report
CREATE TABLE report (
    report_id INT PRIMARY KEY,
    name VARCHAR(40),
    description VARCHAR(40),
    status VARCHAR(40),
    create_by INT,
    FOREIGN KEY (create_by) REFERENCES user(user_id)
);

-- Tạo bảng reportResult
CREATE TABLE reportResult (
    result_id INT PRIMARY KEY,
    report_id INT,
    result_data JSON,
    FOREIGN KEY (report_id) REFERENCES report(report_id)
);

-- Tạo bảng scheduledReport
CREATE TABLE scheduledReport (
    schedule_id INT PRIMARY KEY,
    report_id INT,
    scheduled_time DATE,
    recipient VARCHAR(40),
    FOREIGN KEY (report_id) REFERENCES report(report_id)
);

insert into itss.building
values
(1, 'Mckinnis Building', '2817 3rd Street', 14),
(2, 'Gallegos Building', '40700 Penn Lane', 17),
(3, 'Parker Building', '163 Highwood Drive', 13),
(4, 'Toney Building', '12 Saint Andrews Crossover', 18),
(5, 'Mclarney Building', '7250 Urban Drive', 5),
(6, 'Myers Building', '1811 1st Street Northwest', 8),
(7, 'Oge Building', '2909 Flamstead Road', 18),
(8, 'Beal Building', '22825 Paseo Place', 5),
(9, 'Carrillo Building', '5650 West 60th Avenue', 15),
(10, 'Macneil Building', '7256 West 84th Way', 18),
(11, 'Harian Building', '625 West Main Street', 15),
(12, 'Cardenas Building', '1559 Alabama Avenue Southeast', 10),
(13, 'Voss Building', '386 Pratt Road', 14),
(14, 'Jordon Building', '5721 Frank Hough Road', 9),
(15, 'Robinson Building', '3116 Northwest 61st Terrace', 2),
(16, 'Spires Building', '4981 Shirley Way', 16),
(17, 'Helmy Building', '415 Lullwater Drive', 10),
(18, 'Majewski Building', '22 Gallatin Street Northeast', 2),
(19, 'Kelley Building', '2315 Kimberly Drive', 4),
(20, 'Martin Building', '30544 Whitmore Road', 10),
(21, 'Yates Building', '121 Mills Run Drive', 18),
(22, 'Dennis Building', '4632 Vermont 15', 1),
(23, 'Hall Building', '302 Elberta Street', 1),
(24, 'Mincks Building', '7326 North 62nd Avenue', 20),
(25, 'Gutierrez Building', '1452 55th Avenue', 20),
(26, 'Hill Building', '5024 Ryan Drive', 5),
(27, 'Kasack Building', '206 Blue Marlin Drive', 3),
(28, 'Roberts Building', '18 Hamilton Street Northwest', 13),
(29, 'Caplinger Building', '1478 Sharps Point Road', 1),
(30, 'Oswalt Building', '3538 Albemarle Street Northwest', 6),
(31, 'Dorado Building', '6862 North 44th Avenue', 12),
(32, 'Tippin Building', '210 Lacross Lane', 13),
(33, 'Rice Building', '33 South Hill Avenue', 8),
(34, 'Petersen Building', '5170 Hickory Hollow Parkway', 2),
(35, 'Richards Building', '6452 El Camino Real', 18),
(36, 'Serasio Building', '803 18th Avenue South', 1),
(37, 'Torpey Building', '826 South Hancock Street', 14),
(38, 'Magelssen Building', '1427 South Carolina Avenue Southeast', 7),
(39, 'Garza Building', '233 Juniper Drive', 8),
(40, 'Rios Building', '4920 Quonset Drive', 20),
(41, 'Sanchez Building', '1906 Palmetto Avenue', 14),
(42, 'Raney Building', '9200 Gainswood Circle', 17),
(43, 'Howe Building', '826 South Hancock Street', 8),
(44, 'Ford Building', '890 East Skyline Drive', 13),
(45, 'Bretz Building', '102 Old Depot Road', 20),
(46, 'Zeller Building', '6206 Waters Avenue', 11),
(47, 'Baker Building', '6234 West Behrend Drive', 12),
(48, 'Householder Building', '6408 Fern Crest Road', 17),
(49, 'Schultz Building', '208 Kinnaird Lane', 6),
(50, 'Dickson Building', '3147 West Old Farmington Road', 9),
(51, 'Pavia Building', '607 Trails End Road', 12),
(52, 'Clark Building', '4001 Anderson Road', 1),
(53, 'Bouleris Building', '6328 West Brown Street', 15),
(54, 'Rothrock Building', '151 Main Street', 18),
(55, 'Juelfs Building', '1990 Poplar Ridge Road', 17),
(56, 'Cawthorne Building', '4822 Piney Branch Road Northwest', 5),
(57, 'Hernandez Building', '9200 Gainswood Circle', 5),
(58, 'Rutledge Building', '619 Colusa Avenue', 13),
(59, 'Fornili Building', '39 Newhall Street', 4),
(60, 'Heath Building', '1520 H Street Northwest', 9),
(61, 'Posadas Building', '1320 Dunbarton Road', 20),
(62, 'Kresge Building', '1623 Mines Road', 7),
(63, 'Soto Building', '215 9th Street Northeast', 3),
(64, 'Jackson Building', '4001 Anderson Road', 1),
(65, 'Edwards Building', '74 Bradford Road', 20),
(66, 'Shephard Building', '212 Ambleside Drive', 12),
(67, 'Liff Building', '6007 Yarrow Street', 19),
(68, 'Caskey Building', '625 West Main Street', 8),
(69, 'Born Building', '880 General George Patton Road', 19),
(70, 'Anderson Building', '1817 Beech Street', 14),
(71, 'Miller Building', '2868 Peak Road', 1),
(72, 'Kachermeyer Building', '2732 Rosedale Place', 3),
(73, 'Jusino Building', '332 Stafford Road', 2),
(74, 'Merkle Building', '2140 Colony Loop', 12),
(75, 'Elliott Building', '501 Running Creek Place', 15),
(76, 'Alonso Building', '7192 West Topeka Drive', 7),
(77, 'Louie Building', '145 Grau Drive', 18),
(78, 'Estes Building', '3808 South Smiley Circle', 11),
(79, 'Karle Building', '1106 Mission Drive', 19),
(80, 'Lucas Building', '168 Partridge Hill Road', 8),
(81, 'Hammonds Building', '20933 River Park Drive', 11),
(82, 'Braaten Building', '1324 Beddington Park', 8),
(83, 'Debartolo Building', '1809 Hughes Street', 4),
(84, 'Medina Building', '7187 Holland Court', 3),
(85, 'Diaz Building', '600 12th Avenue South', 10),
(86, 'Jones Building', '4360 Hagan Road', 6),
(87, 'Benoit Building', '72 Milford Road', 14),
(88, 'Ruiz Building', '5624 Oakes Drive', 4),
(89, 'Roberson Building', '9106 Ellis Way', 18),
(90, 'Hammond Building', '2902 Flint Street', 14),
(91, 'Painter Building', '1114 Barley Drive', 5),
(92, 'Bartos Building', '802 Madison Street Northwest', 5),
(93, 'Eckard Building', '91 Rushforde Drive', 16),
(94, 'Shuffler Building', '144 Lauderdale Street', 19),
(95, 'Mcneil Building', '1430 South Gay Avenue', 20),
(96, 'Brendan Building', '6609 Westshire Drive', 18),
(97, 'Jonathan Building', '4333 Catherine Street', 10),
(98, 'Nancy Building', '6657 West Rose Garden Lane', 20),
(99, 'Sandra Building', '1310 Riggs Street Northwest', 19),
(100, 'Beverly Building', '8277 Lamar Place', 6);

insert into itss.apartment
values
(1, 19, 5, 99, 3752),
(2, 9, 78, 32, 2597),
(3, 55, 79, 75, 4663),
(4, 74, 80, 49, 1886),
(5, 54, 50, 95, 3779),
(6, 95, 46, 78, 2580),
(7, 92, 97, 69, 3961),
(8, 1, 90, 77, 1725),
(9, 65, 95, 48, 1949),
(10, 9, 82, 87, 3327),
(11, 47, 16, 97, 4308),
(12, 32, 70, 95, 2762),
(13, 37, 68, 69, 3025),
(14, 94, 33, 46, 3407),
(15, 76, 69, 64, 1745),
(16, 51, 87, 84, 2035),
(17, 70, 24, 55, 1960),
(18, 70, 34, 62, 2287),
(19, 70, 79, 44, 2095),
(20, 11, 46, 42, 1322),
(21, 56, 64, 82, 2563),
(22, 21, 12, 64, 4840),
(23, 5, 89, 61, 3666),
(24, 63, 44, 87, 4798),
(25, 43, 55, 60, 1592),
(26, 14, 72, 36, 4648),
(27, 94, 1, 30, 1404),
(28, 83, 8, 68, 4930),
(29, 36, 90, 44, 2447),
(30, 50, 13, 51, 2541),
(31, 64, 43, 67, 1947),
(32, 94, 39, 61, 4610),
(33, 54, 70, 56, 1030),
(34, 43, 37, 90, 2896),
(35, 24, 79, 48, 1605),
(36, 35, 47, 67, 2554),
(37, 42, 33, 100, 1023),
(38, 48, 60, 90, 1136),
(39, 7, 36, 76, 1791),
(40, 97, 97, 97, 2931),
(41, 50, 19, 85, 3944),
(42, 39, 42, 77, 1016),
(43, 50, 46, 40, 4256),
(44, 56, 64, 38, 2762),
(45, 68, 24, 72, 1170),
(46, 28, 16, 46, 2045),
(47, 23, 48, 55, 2806),
(48, 66, 73, 37, 1027),
(49, 13, 81, 59, 4495),
(50, 80, 87, 73, 3245),
(51, 31, 59, 95, 4446),
(52, 28, 12, 55, 1043),
(53, 98, 50, 43, 3867),
(54, 48, 23, 44, 1797),
(55, 34, 61, 51, 2357),
(56, 43, 100, 53, 2239),
(57, 30, 52, 42, 2304),
(58, 94, 91, 58, 1840),
(59, 90, 81, 86, 2265),
(60, 65, 36, 40, 3156),
(61, 48, 36, 42, 2078),
(62, 92, 50, 58, 2793),
(63, 17, 73, 100, 4167),
(64, 56, 74, 30, 3910),
(65, 29, 89, 73, 2861),
(66, 11, 40, 66, 2389),
(67, 14, 39, 73, 3961),
(68, 83, 32, 30, 2751),
(69, 36, 31, 60, 2405),
(70, 73, 88, 86, 3680),
(71, 77, 1, 41, 1435),
(72, 96, 31, 43, 1418),
(73, 22, 4, 81, 1218),
(74, 44, 86, 72, 2976),
(75, 83, 94, 67, 3796),
(76, 7, 99, 87, 3185),
(77, 3, 22, 75, 4377),
(78, 87, 90, 34, 2420),
(79, 45, 58, 51, 2526),
(80, 64, 40, 42, 3675),
(81, 89, 36, 32, 3033),
(82, 12, 25, 95, 3917),
(83, 84, 97, 47, 3334),
(84, 47, 47, 64, 1018),
(85, 64, 56, 37, 3106),
(86, 42, 48, 50, 1784),
(87, 96, 78, 68, 4216),
(88, 92, 68, 96, 2562),
(89, 97, 96, 41, 2289),
(90, 25, 3, 75, 2773),
(91, 96, 92, 36, 1187),
(92, 78, 32, 73, 2430),
(93, 49, 43, 49, 1242),
(94, 90, 15, 64, 2475),
(95, 15, 17, 50, 1447),
(96, 84, 94, 85, 1179),
(97, 47, 100, 54, 4395),
(98, 47, 74, 65, 3511),
(99, 44, 88, 95, 1606),
(100, 32, 85, 96, 4719);

insert into itss.tenant
values
(1, 13, 'Claude Taylor', '210791567', 'INACTIVE'),
(2, 70, 'Chantell Dennis', '284426292', 'INACTIVE'),
(3, 6, 'Celestina Fimbres', '693737837', 'ACTIVE'),
(4, 28, 'Pearl Carter', '764382729', 'ACTIVE'),
(5, 81, 'Frank Williams', '782385794', 'ACTIVE'),
(6, 58, 'Katherine Vetter', '341718737', 'INACTIVE'),
(7, 77, 'Julian Tijerina', '368932469', 'ACTIVE'),
(8, 2, 'Ronald Bruce', '130803908', 'ACTIVE'),
(9, 26, 'Teresa Houston', '658556298', 'INACTIVE'),
(10, 87, 'Jerilyn Denault', '305853366', 'ACTIVE'),
(11, 72, 'Elsie Blandy', '913774374', 'ACTIVE'),
(12, 20, 'Grover Peterson', '785049369', 'ACTIVE'),
(13, 84, 'Ricky Brickey', '424162025', 'ACTIVE'),
(14, 10, 'Raymond Compo', '583939972', 'INACTIVE'),
(15, 85, 'Jeannie Hood', '299478814', 'ACTIVE'),
(16, 82, 'Anna Gaspar', '887663234', 'ACTIVE'),
(17, 96, 'Lakisha Andreasen', '934790172', 'ACTIVE'),
(18, 11, 'Davis Sorenson', '813868452', 'INACTIVE'),
(19, 81, 'Prince Saunders', '691915610', 'INACTIVE'),
(20, 9, 'Jason Mui', '760622931', 'INACTIVE'),
(21, 21, 'Charles Keithly', '511855067', 'INACTIVE'),
(22, 18, 'Joseph Anderson', '861899879', 'INACTIVE'),
(23, 60, 'John Gatlin', '245702954', 'INACTIVE'),
(24, 54, 'Mildred Pathak', '316243121', 'ACTIVE'),
(25, 51, 'Jose Heredia', '647224731', 'ACTIVE'),
(26, 52, 'Darlene Casanova', '195867922', 'ACTIVE'),
(27, 11, 'Betty Beam', '176439292', 'INACTIVE'),
(28, 6, 'Bertram Hinson', '837143837', 'INACTIVE'),
(29, 96, 'Michael Thompson', '916230854', 'INACTIVE'),
(30, 94, 'Jose Wilson', '821648721', 'INACTIVE'),
(31, 61, 'Leo Philbrick', '859538407', 'INACTIVE'),
(32, 82, 'Shannon Donahue', '133367441', 'ACTIVE'),
(33, 68, 'Holly Larue', '380364743', 'ACTIVE'),
(34, 80, 'Erik Darling', '624337589', 'INACTIVE'),
(35, 17, 'Tana Garcia', '511843337', 'INACTIVE'),
(36, 53, 'Richard Lemke', '526283334', 'INACTIVE'),
(37, 69, 'Lois Fernandez', '111847162', 'INACTIVE'),
(38, 85, 'Richard Martinez', '389917293', 'INACTIVE'),
(39, 92, 'Shelly Ruelas', '964269814', 'INACTIVE'),
(40, 94, 'Ann Bartus', '422492247', 'INACTIVE'),
(41, 29, 'Reta Lanphear', '665132673', 'ACTIVE'),
(42, 45, 'Sherri Hoffman', '703758585', 'INACTIVE'),
(43, 30, 'Barney Ericksen', '234600082', 'ACTIVE'),
(44, 66, 'Larry Lee', '184946373', 'ACTIVE'),
(45, 89, 'Sandra Holly', '927660977', 'ACTIVE'),
(46, 39, 'Teresa Bailey', '638069800', 'INACTIVE'),
(47, 81, 'Pat Hinton', '488652467', 'INACTIVE'),
(48, 65, 'Linda Pensinger', '607024059', 'INACTIVE'),
(49, 31, 'Kelly Gonzalez', '123866939', 'INACTIVE'),
(50, 84, 'Katrina Couto', '204851519', 'INACTIVE'),
(51, 82, 'Christina Friday', '641881895', 'INACTIVE'),
(52, 85, 'Andres Sisk', '969780056', 'ACTIVE'),
(53, 22, 'Edward Rohde', '431880435', 'ACTIVE'),
(54, 67, 'Maria Sabatini', '763504445', 'INACTIVE'),
(55, 74, 'Myrna King', '888518604', 'INACTIVE'),
(56, 31, 'Anna Gass', '486508945', 'ACTIVE'),
(57, 72, 'Tanya Smith', '179081025', 'INACTIVE'),
(58, 6, 'Jonathan Pothier', '476154463', 'INACTIVE'),
(59, 100, 'Joetta Aldrich', '184871962', 'ACTIVE'),
(60, 99, 'Nathan Stubblefield', '498709277', 'ACTIVE'),
(61, 61, 'Jose Lepley', '369001482', 'INACTIVE'),
(62, 5, 'Peggy Tartt', '329606164', 'ACTIVE'),
(63, 61, 'Latoyia Brock', '479508009', 'INACTIVE'),
(64, 42, 'Edith Atwood', '265844868', 'ACTIVE'),
(65, 6, 'Nell Meyer', '684394893', 'INACTIVE'),
(66, 20, 'Jeffery Lewis', '305787083', 'INACTIVE'),
(67, 87, 'Vina Wilmot', '168286700', 'INACTIVE'),
(68, 26, 'Joellen Hill', '527068973', 'INACTIVE'),
(69, 15, 'Palmer Mouton', '941769488', 'ACTIVE'),
(70, 43, 'Christel Dwyer', '233922925', 'INACTIVE'),
(71, 32, 'Nicholas Torres', '693449460', 'ACTIVE'),
(72, 54, 'Danielle Finley', '274891753', 'INACTIVE'),
(73, 44, 'Ed Depedro', '897672134', 'INACTIVE'),
(74, 28, 'Jeffrey Byrd', '129071417', 'ACTIVE'),
(75, 62, 'Betty Midkiff', '904232719', 'INACTIVE'),
(76, 72, 'Daniel Shoemaker', '853841988', 'INACTIVE'),
(77, 13, 'Matthew Hawk', '441284714', 'INACTIVE'),
(78, 32, 'Arlinda Schier', '721077716', 'INACTIVE'),
(79, 58, 'Edna Cooper', '904240963', 'INACTIVE'),
(80, 72, 'Nicole Gugler', '241509068', 'ACTIVE'),
(81, 66, 'Windy Davis', '480835232', 'INACTIVE'),
(82, 26, 'Nancy Brady', '760562391', 'ACTIVE'),
(83, 26, 'James Cagney', '266496065', 'ACTIVE'),
(84, 6, 'Jeffrey Williams', '445569855', 'INACTIVE'),
(85, 92, 'Fred Marinello', '299185503', 'ACTIVE'),
(86, 58, 'Elizabeth Donart', '991575575', 'ACTIVE'),
(87, 8, 'Ray Duplessis', '893556306', 'ACTIVE'),
(88, 97, 'Tynisha Gasaway', '205308986', 'ACTIVE'),
(89, 22, 'Fredrick Kirk', '891769944', 'INACTIVE'),
(90, 66, 'Michael Lewis', '457038227', 'ACTIVE'),
(91, 8, 'Gayle Trent', '413104248', 'ACTIVE'),
(92, 50, 'William Pacheco', '860788387', 'ACTIVE'),
(93, 12, 'Clint Black', '780028479', 'INACTIVE'),
(94, 51, 'James Breen', '249334845', 'ACTIVE'),
(95, 43, 'Chuck Aaronson', '868291854', 'ACTIVE'),
(96, 90, 'Sally Hanes', '409375906', 'ACTIVE'),
(97, 19, 'Bonnie Washington', '912717437', 'INACTIVE'),
(98, 15, 'Joan Mcallister', '405895932', 'ACTIVE'),
(99, 80, 'Manuel Godoy', '525274173', 'ACTIVE'),
(100, 38, 'Marilyn Angle', '843525367', 'ACTIVE');

insert into itss.contract
values
(1, 90, '2020-11-25', '2023-11-1', '', 6768),
(2, 55, '2019-11-20', '2023-9-26', '', 8407),
(3, 5, '2019-12-26', '2021-9-20', '', 1526),
(4, 55, '2016-6-27', '2022-7-18', '', 8928),
(5, 87, '2016-6-28', '2021-2-27', '', 4508),
(6, 87, '2015-11-28', '2022-9-10', '', 8115),
(7, 34, '2017-9-7', '2022-9-8', '', 1298),
(8, 86, '2019-8-14', '2021-8-12', '', 3153),
(9, 44, '2019-12-20', '2023-10-21', '', 7112),
(10, 27, '2020-4-7', '2021-1-29', '', 6891),
(11, 27, '2020-12-19', '2022-2-14', '', 4737),
(12, 94, '2019-2-24', '2021-8-13', '', 3099),
(13, 99, '2015-5-21', '2023-6-7', '', 1569),
(14, 82, '2015-3-16', '2023-6-12', '', 6086),
(15, 95, '2017-1-12', '2022-2-22', '', 6102),
(16, 59, '2020-9-15', '2023-4-14', '', 9936),
(17, 78, '2016-11-19', '2023-7-17', '', 9532),
(18, 53, '2017-11-12', '2021-5-19', '', 8900),
(19, 29, '2015-7-18', '2022-7-25', '', 9898),
(20, 97, '2018-5-21', '2021-8-1', '', 5056),
(21, 25, '2019-2-22', '2022-11-16', '', 1479),
(22, 19, '2020-8-3', '2023-1-26', '', 9445),
(23, 8, '2017-9-27', '2022-2-26', '', 3062),
(24, 12, '2015-8-1', '2023-5-26', '', 2586),
(25, 32, '2015-4-25', '2023-12-20', '', 1922),
(26, 37, '2019-4-17', '2023-6-22', '', 4733),
(27, 38, '2018-7-19', '2021-12-14', '', 4258),
(28, 8, '2020-11-1', '2021-10-22', '', 3465),
(29, 29, '2018-8-22', '2022-7-26', '', 8758),
(30, 23, '2015-7-20', '2022-4-5', '', 6924),
(31, 20, '2018-12-17', '2022-6-6', '', 7837),
(32, 11, '2015-5-19', '2023-3-13', '', 6835),
(33, 80, '2015-9-28', '2022-7-19', '', 7616),
(34, 64, '2020-9-10', '2021-5-26', '', 4376),
(35, 43, '2020-2-4', '2023-8-6', '', 6539),
(36, 50, '2017-9-28', '2023-1-19', '', 9195),
(37, 45, '2018-6-13', '2022-11-2', '', 7761),
(38, 69, '2015-9-29', '2023-3-1', '', 7343),
(39, 29, '2015-4-21', '2023-7-29', '', 5702),
(40, 94, '2016-10-14', '2023-7-2', '', 5613),
(41, 81, '2020-1-19', '2022-12-6', '', 4396),
(42, 45, '2017-8-16', '2023-12-4', '', 6851),
(43, 11, '2015-12-22', '2022-2-18', '', 1705),
(44, 11, '2018-6-5', '2022-11-11', '', 4267),
(45, 55, '2016-12-29', '2022-4-14', '', 1216),
(46, 84, '2016-11-16', '2023-1-28', '', 2709),
(47, 62, '2019-8-26', '2021-12-27', '', 3555),
(48, 88, '2016-10-10', '2021-4-9', '', 3405),
(49, 81, '2016-9-9', '2022-3-27', '', 1801),
(50, 100, '2019-3-3', '2022-3-3', '', 1804),
(51, 59, '2017-6-6', '2021-3-16', '', 6360),
(52, 59, '2018-6-22', '2022-9-17', '', 9068),
(53, 6, '2018-5-18', '2021-11-19', '', 2925),
(54, 6, '2015-11-20', '2023-2-24', '', 8140),
(55, 11, '2020-3-11', '2022-11-15', '', 2517),
(56, 73, '2017-5-16', '2022-10-16', '', 9193),
(57, 93, '2015-5-21', '2023-4-20', '', 5335),
(58, 48, '2015-12-18', '2021-1-6', '', 7310),
(59, 38, '2020-10-15', '2022-6-15', '', 5967),
(60, 90, '2017-12-1', '2023-10-11', '', 2458),
(61, 35, '2017-6-4', '2023-2-10', '', 6888),
(62, 81, '2015-9-5', '2022-9-8', '', 1517),
(63, 14, '2017-4-3', '2023-8-25', '', 8537),
(64, 71, '2020-9-23', '2022-9-26', '', 8122),
(65, 27, '2019-12-14', '2023-2-2', '', 6542),
(66, 60, '2015-10-6', '2023-8-23', '', 4578),
(67, 29, '2015-1-19', '2021-10-5', '', 7654),
(68, 77, '2017-11-14', '2023-7-26', '', 1304),
(69, 1, '2015-5-21', '2022-12-6', '', 1518),
(70, 12, '2015-12-3', '2021-3-19', '', 6486),
(71, 22, '2015-8-25', '2021-7-12', '', 5091),
(72, 84, '2017-12-15', '2021-3-25', '', 1151),
(73, 23, '2015-3-10', '2023-3-13', '', 7826),
(74, 93, '2019-4-20', '2023-10-24', '', 3373),
(75, 82, '2016-8-18', '2023-8-25', '', 6043),
(76, 75, '2019-5-13', '2022-1-8', '', 4427),
(77, 29, '2015-9-19', '2021-6-10', '', 9445),
(78, 99, '2016-4-29', '2023-11-6', '', 5405),
(79, 13, '2015-10-6', '2021-3-18', '', 2971),
(80, 93, '2020-11-5', '2021-4-23', '', 5328),
(81, 60, '2015-9-23', '2022-2-25', '', 8476),
(82, 38, '2017-6-8', '2023-4-8', '', 4290),
(83, 63, '2017-1-20', '2021-5-20', '', 5315),
(84, 100, '2020-4-15', '2022-2-6', '', 5592),
(85, 68, '2017-2-16', '2021-8-1', '', 7672),
(86, 55, '2019-1-21', '2022-12-8', '', 6353),
(87, 18, '2020-10-27', '2021-4-14', '', 4217),
(88, 78, '2015-2-18', '2022-9-5', '', 1245),
(89, 100, '2020-1-28', '2023-2-25', '', 3884),
(90, 3, '2019-9-13', '2022-9-19', '', 4549),
(91, 28, '2019-3-12', '2021-4-25', '', 8085),
(92, 50, '2015-5-1', '2022-6-29', '', 6723),
(93, 62, '2018-7-13', '2022-6-25', '', 2655),
(94, 90, '2017-6-19', '2022-11-1', '', 4919),
(95, 41, '2018-3-23', '2023-3-19', '', 1588),
(96, 98, '2020-1-3', '2022-12-26', '', 3086),
(97, 37, '2019-5-14', '2021-10-25', '', 4697),
(98, 66, '2020-4-16', '2022-10-12', '', 2802),
(99, 76, '2018-1-3', '2022-12-29', '', 2076),
(100, 18, '2020-8-1', '2021-5-11', '', 2484);

insert into itss.payment
values
(1, 84, '2024-3-6', 9177),
(2, 40, '2024-3-16', 7256),
(3, 83, '2025-2-5', 9850),
(4, 68, '2024-10-12', 4717),
(5, 6, '2024-12-28', 2934),
(6, 99, '2024-4-14', 4838),
(7, 70, '2024-6-17', 3201),
(8, 60, '2024-7-4', 5504),
(9, 99, '2025-1-11', 4743),
(10, 97, '2025-9-4', 6172),
(11, 57, '2024-11-12', 5570),
(12, 63, '2024-2-22', 4343),
(13, 72, '2025-2-5', 1415),
(14, 39, '2024-10-27', 2366),
(15, 81, '2025-2-1', 8630),
(16, 66, '2025-5-8', 5291),
(17, 83, '2025-1-14', 7196),
(18, 69, '2025-4-24', 6449),
(19, 87, '2025-8-7', 2471),
(20, 69, '2025-1-8', 8592),
(21, 39, '2025-1-7', 7948),
(22, 38, '2024-6-3', 9259),
(23, 12, '2024-5-15', 7318),
(24, 7, '2024-4-23', 4426),
(25, 97, '2024-3-17', 2243),
(26, 27, '2025-1-24', 3437),
(27, 7, '2025-12-9', 6429),
(28, 34, '2024-12-16', 2669),
(29, 95, '2025-1-24', 7903),
(30, 5, '2024-3-13', 2074),
(31, 92, '2025-3-3', 5824),
(32, 8, '2024-5-7', 1434),
(33, 13, '2025-5-24', 8012),
(34, 62, '2024-1-12', 8767),
(35, 46, '2025-4-29', 6848),
(36, 78, '2025-3-27', 1289),
(37, 12, '2025-2-18', 1047),
(38, 96, '2025-1-21', 1054),
(39, 65, '2024-12-11', 9741),
(40, 36, '2025-7-5', 9685),
(41, 37, '2024-9-27', 6185),
(42, 45, '2024-9-11', 1416),
(43, 65, '2024-6-18', 1430),
(44, 65, '2025-8-1', 5864),
(45, 62, '2025-10-28', 6256),
(46, 80, '2025-6-18', 8284),
(47, 63, '2025-10-6', 6501),
(48, 27, '2025-3-17', 3097),
(49, 32, '2025-2-28', 5869),
(50, 93, '2024-3-9', 5384),
(51, 64, '2025-11-27', 1112),
(52, 67, '2024-12-10', 7905),
(53, 94, '2025-9-15', 2353),
(54, 44, '2025-4-17', 8517),
(55, 11, '2025-5-5', 1336),
(56, 21, '2024-11-25', 5202),
(57, 83, '2024-9-5', 5569),
(58, 26, '2024-8-9', 2206),
(59, 7, '2024-2-10', 6111),
(60, 17, '2024-12-11', 8271),
(61, 18, '2025-12-17', 6374),
(62, 84, '2024-7-2', 4341),
(63, 75, '2025-3-12', 8527),
(64, 32, '2025-5-8', 7106),
(65, 58, '2024-4-26', 9499),
(66, 19, '2025-11-12', 5488),
(67, 56, '2024-11-14', 3008),
(68, 87, '2024-5-20', 4989),
(69, 67, '2024-9-19', 2271),
(70, 52, '2025-11-1', 1368),
(71, 86, '2024-10-17', 4853),
(72, 86, '2025-10-24', 7538),
(73, 60, '2024-2-27', 3142),
(74, 35, '2024-4-16', 4932),
(75, 9, '2024-7-28', 8787),
(76, 41, '2025-4-22', 2960),
(77, 16, '2024-9-14', 6517),
(78, 52, '2024-1-15', 6357),
(79, 92, '2025-11-16', 5457),
(80, 27, '2025-5-28', 1998),
(81, 29, '2025-11-2', 6804),
(82, 79, '2025-11-6', 7213),
(83, 32, '2025-8-22', 2788),
(84, 1, '2024-10-4', 9580),
(85, 100, '2024-6-11', 9410),
(86, 80, '2024-4-11', 3448),
(87, 77, '2025-6-25', 9977),
(88, 38, '2024-10-24', 4535),
(89, 71, '2024-3-2', 4658),
(90, 14, '2025-7-14', 2408),
(91, 77, '2024-5-9', 8487),
(92, 65, '2025-2-18', 6317),
(93, 39, '2025-5-4', 8620),
(94, 77, '2025-4-11', 8637),
(95, 78, '2024-2-24', 7881),
(96, 24, '2024-1-14', 4108),
(97, 98, '2024-7-28', 8841),
(98, 5, '2025-2-17', 9112),
(99, 69, '2024-10-28', 6894),
(100, 9, '2025-1-11', 3777);

insert into itss.user
values
(1, 'user1', 'password1', 'Tenant'),
(2, 'user2', 'password2', 'Tenant'),
(3, 'user3', 'password3', 'Tenant'),
(4, 'user4', 'password4', 'Tenant'),
(5, 'user5', 'password5', 'Tenant'),
(6, 'user6', 'password6', 'Tenant'),
(7, 'user7', 'password7', 'Tenant'),
(8, 'user8', 'password8', 'Tenant'),
(9, 'user9', 'password9', 'Tenant'),
(10, 'user10', 'password10', 'Tenant'),
(11, 'user11', 'password11', 'Tenant'),
(12, 'user12', 'password12', 'Tenant'),
(13, 'user13', 'password13', 'Tenant'),
(14, 'user14', 'password14', 'Tenant'),
(15, 'user15', 'password15', 'Tenant'),
(16, 'user16', 'password16', 'Tenant'),
(17, 'user17', 'password17', 'Tenant'),
(18, 'user18', 'password18', 'Tenant'),
(19, 'user19', 'password19', 'Tenant'),
(20, 'user20', 'password20', 'Tenant'),
(21, 'user21', 'password21', 'Tenant'),
(22, 'user22', 'password22', 'Tenant'),
(23, 'user23', 'password23', 'Tenant'),
(24, 'user24', 'password24', 'Tenant'),
(25, 'user25', 'password25', 'Tenant'),
(26, 'user26', 'password26', 'Tenant'),
(27, 'user27', 'password27', 'Tenant'),
(28, 'user28', 'password28', 'Tenant'),
(29, 'user29', 'password29', 'Tenant'),
(30, 'user30', 'password30', 'Tenant'),
(31, 'user31', 'password31', 'Tenant'),
(32, 'user32', 'password32', 'Tenant'),
(33, 'user33', 'password33', 'Tenant'),
(34, 'user34', 'password34', 'Tenant'),
(35, 'user35', 'password35', 'Tenant'),
(36, 'user36', 'password36', 'Tenant'),
(37, 'user37', 'password37', 'Tenant'),
(38, 'user38', 'password38', 'Tenant'),
(39, 'user39', 'password39', 'Tenant'),
(40, 'user40', 'password40', 'Tenant'),
(41, 'user41', 'password41', 'Tenant'),
(42, 'user42', 'password42', 'Tenant'),
(43, 'user43', 'password43', 'Tenant'),
(44, 'user44', 'password44', 'Tenant'),
(45, 'user45', 'password45', 'Tenant'),
(46, 'user46', 'password46', 'Tenant'),
(47, 'user47', 'password47', 'Tenant'),
(48, 'user48', 'password48', 'Tenant'),
(49, 'user49', 'password49', 'Tenant'),
(50, 'user50', 'password50', 'Tenant'),
(51, 'user51', 'password51', 'Tenant'),
(52, 'user52', 'password52', 'Tenant'),
(53, 'user53', 'password53', 'Tenant'),
(54, 'user54', 'password54', 'Tenant'),
(55, 'user55', 'password55', 'Tenant'),
(56, 'user56', 'password56', 'Tenant'),
(57, 'user57', 'password57', 'Tenant'),
(58, 'user58', 'password58', 'Tenant'),
(59, 'user59', 'password59', 'Tenant'),
(60, 'user60', 'password60', 'Tenant'),
(61, 'user61', 'password61', 'Tenant'),
(62, 'user62', 'password62', 'Tenant'),
(63, 'user63', 'password63', 'Tenant'),
(64, 'user64', 'password64', 'Tenant'),
(65, 'user65', 'password65', 'Tenant'),
(66, 'user66', 'password66', 'Tenant'),
(67, 'user67', 'password67', 'Tenant'),
(68, 'user68', 'password68', 'Tenant'),
(69, 'user69', 'password69', 'Tenant'),
(70, 'user70', 'password70', 'Tenant'),
(71, 'user71', 'password71', 'Tenant'),
(72, 'user72', 'password72', 'Tenant'),
(73, 'user73', 'password73', 'Tenant'),
(74, 'user74', 'password74', 'Tenant'),
(75, 'user75', 'password75', 'Tenant'),
(76, 'user76', 'password76', 'Tenant'),
(77, 'user77', 'password77', 'Tenant'),
(78, 'user78', 'password78', 'Tenant'),
(79, 'user79', 'password79', 'Tenant'),
(80, 'user80', 'password80', 'Tenant'),
(81, 'user81', 'password81', 'Tenant'),
(82, 'user82', 'password82', 'Tenant'),
(83, 'user83', 'password83', 'Tenant'),
(84, 'user84', 'password84', 'Tenant'),
(85, 'user85', 'password85', 'Tenant'),
(86, 'user86', 'password86', 'Tenant'),
(87, 'user87', 'password87', 'Tenant'),
(88, 'user88', 'password88', 'Tenant'),
(89, 'user89', 'password89', 'Tenant'),
(90, 'user90', 'password90', 'Tenant'),
(91, 'user91', 'password91', 'Tenant'),
(92, 'user92', 'password92', 'Tenant'),
(93, 'user93', 'password93', 'Tenant'),
(94, 'user94', 'password94', 'Tenant'),
(95, 'user95', 'password95', 'Tenant'),
(96, 'user96', 'password96', 'Tenant'),
(97, 'user97', 'password97', 'Tenant'),
(98, 'user98', 'password98', 'Tenant'),
(99, 'user99', 'password99', 'Tenant'),
(100, 'user100', 'password100', 'Tenant');

ALTER TABLE user
ADD tenant_id int;

UPDATE user SET tenant_id = 100 WHERE user_id = 1;
UPDATE user SET tenant_id = 2 WHERE user_id = 2;
UPDATE user SET tenant_id = 10 WHERE user_id = 3;
UPDATE user SET tenant_id = 39 WHERE user_id = 4;
UPDATE user SET tenant_id = 27 WHERE user_id = 5;
UPDATE user SET tenant_id = 36 WHERE user_id = 6;
UPDATE user SET tenant_id = 67 WHERE user_id = 7;
UPDATE user SET tenant_id = 53 WHERE user_id = 8;
UPDATE user SET tenant_id = 37 WHERE user_id = 9;
UPDATE user SET tenant_id = 69 WHERE user_id = 10;
UPDATE user SET tenant_id = 21 WHERE user_id = 11;
UPDATE user SET tenant_id = 41 WHERE user_id = 12;
UPDATE user SET tenant_id = 48 WHERE user_id = 13;
UPDATE user SET tenant_id = 100 WHERE user_id = 14;
UPDATE user SET tenant_id = 50 WHERE user_id = 15;
UPDATE user SET tenant_id = 74 WHERE user_id = 16;
UPDATE user SET tenant_id = 84 WHERE user_id = 17;
UPDATE user SET tenant_id = 25 WHERE user_id = 18;
UPDATE user SET tenant_id = 89 WHERE user_id = 19;
UPDATE user SET tenant_id = 97 WHERE user_id = 20;
UPDATE user SET tenant_id = 21 WHERE user_id = 21;
UPDATE user SET tenant_id = 58 WHERE user_id = 22;
UPDATE user SET tenant_id = 73 WHERE user_id = 23;
UPDATE user SET tenant_id = 53 WHERE user_id = 24;
UPDATE user SET tenant_id = 23 WHERE user_id = 25;
UPDATE user SET tenant_id = 12 WHERE user_id = 26;
UPDATE user SET tenant_id = 42 WHERE user_id = 27;
UPDATE user SET tenant_id = 26 WHERE user_id = 28;
UPDATE user SET tenant_id = 37 WHERE user_id = 29;
UPDATE user SET tenant_id = 87 WHERE user_id = 30;
UPDATE user SET tenant_id = 47 WHERE user_id = 31;
UPDATE user SET tenant_id = 33 WHERE user_id = 32;
UPDATE user SET tenant_id = 46 WHERE user_id = 33;
UPDATE user SET tenant_id = 4 WHERE user_id = 34;
UPDATE user SET tenant_id = 70 WHERE user_id = 35;
UPDATE user SET tenant_id = 53 WHERE user_id = 36;
UPDATE user SET tenant_id = 49 WHERE user_id = 37;
UPDATE user SET tenant_id = 59 WHERE user_id = 38;
UPDATE user SET tenant_id = 4 WHERE user_id = 39;
UPDATE user SET tenant_id = 27 WHERE user_id = 40;
UPDATE user SET tenant_id = 18 WHERE user_id = 41;
UPDATE user SET tenant_id = 75 WHERE user_id = 42;
UPDATE user SET tenant_id = 32 WHERE user_id = 43;
UPDATE user SET tenant_id = 43 WHERE user_id = 44;
UPDATE user SET tenant_id = 98 WHERE user_id = 45;
UPDATE user SET tenant_id = 89 WHERE user_id = 46;
UPDATE user SET tenant_id = 95 WHERE user_id = 47;
UPDATE user SET tenant_id = 37 WHERE user_id = 48;
UPDATE user SET tenant_id = 98 WHERE user_id = 49;
UPDATE user SET tenant_id = 78 WHERE user_id = 50;
UPDATE user SET tenant_id = 48 WHERE user_id = 51;
UPDATE user SET tenant_id = 67 WHERE user_id = 52;
UPDATE user SET tenant_id = 62 WHERE user_id = 53;
UPDATE user SET tenant_id = 8 WHERE user_id = 54;
UPDATE user SET tenant_id = 78 WHERE user_id = 55;
UPDATE user SET tenant_id = 64 WHERE user_id = 56;
UPDATE user SET tenant_id = 85 WHERE user_id = 57;
UPDATE user SET tenant_id = 54 WHERE user_id = 58;
UPDATE user SET tenant_id = 69 WHERE user_id = 59;
UPDATE user SET tenant_id = 94 WHERE user_id = 60;
UPDATE user SET tenant_id = 23 WHERE user_id = 61;
UPDATE user SET tenant_id = 51 WHERE user_id = 62;
UPDATE user SET tenant_id = 20 WHERE user_id = 63;
UPDATE user SET tenant_id = 83 WHERE user_id = 64;
UPDATE user SET tenant_id = 60 WHERE user_id = 65;
UPDATE user SET tenant_id = 83 WHERE user_id = 66;
UPDATE user SET tenant_id = 6 WHERE user_id = 67;
UPDATE user SET tenant_id = 12 WHERE user_id = 68;
UPDATE user SET tenant_id = 69 WHERE user_id = 69;
UPDATE user SET tenant_id = 15 WHERE user_id = 70;
UPDATE user SET tenant_id = 18 WHERE user_id = 71;
UPDATE user SET tenant_id = 70 WHERE user_id = 72;
UPDATE user SET tenant_id = 89 WHERE user_id = 73;
UPDATE user SET tenant_id = 59 WHERE user_id = 74;
UPDATE user SET tenant_id = 1 WHERE user_id = 75;
UPDATE user SET tenant_id = 35 WHERE user_id = 76;
UPDATE user SET tenant_id = 79 WHERE user_id = 77;
UPDATE user SET tenant_id = 98 WHERE user_id = 78;
UPDATE user SET tenant_id = 82 WHERE user_id = 79;
UPDATE user SET tenant_id = 26 WHERE user_id = 80;
UPDATE user SET tenant_id = 85 WHERE user_id = 81;
UPDATE user SET tenant_id = 44 WHERE user_id = 82;
UPDATE user SET tenant_id = 48 WHERE user_id = 83;
UPDATE user SET tenant_id = 48 WHERE user_id = 84;
UPDATE user SET tenant_id = 30 WHERE user_id = 85;
UPDATE user SET tenant_id = 6 WHERE user_id = 86;
UPDATE user SET tenant_id = 28 WHERE user_id = 87;
UPDATE user SET tenant_id = 33 WHERE user_id = 88;
UPDATE user SET tenant_id = 2 WHERE user_id = 89;
UPDATE user SET tenant_id = 60 WHERE user_id = 90;
UPDATE user SET tenant_id = 21 WHERE user_id = 91;
UPDATE user SET tenant_id = 81 WHERE user_id = 92;
UPDATE user SET tenant_id = 19 WHERE user_id = 93;
UPDATE user SET tenant_id = 87 WHERE user_id = 94;
UPDATE user SET tenant_id = 15 WHERE user_id = 95;
UPDATE user SET tenant_id = 83 WHERE user_id = 96;
UPDATE user SET tenant_id = 88 WHERE user_id = 97;
UPDATE user SET tenant_id = 36 WHERE user_id = 98;
UPDATE user SET tenant_id = 9 WHERE user_id = 99;
UPDATE user SET tenant_id = 51 WHERE user_id = 100;


alter table contract
modify column notes varchar(1000);

UPDATE contract SET notes = 'Check if the area is secure and close to amenities such as schools, hospitals, and stores.' WHERE contract_id = 1;
UPDATE contract SET notes = 'Understand the conditions and fees for canceling the lease before the agreed-upon term.' WHERE contract_id = 2;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 3;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 4;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 5;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 6;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 7;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 8;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 9;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 10;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 11;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 12;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 13;
UPDATE contract SET notes = 'Check if the area is secure and close to amenities such as schools, hospitals, and stores.' WHERE contract_id = 14;
UPDATE contract SET notes = 'Determine the rental period and consider the possibility of extending the contract after that time.' WHERE contract_id = 15;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 16;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 17;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 18;
UPDATE contract SET notes = 'Read the lease agreement thoroughly to understand the terms and conditions. Pay attention to the lease period, cancellation conditions, and other provisions.' WHERE contract_id = 19;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 20;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 21;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 22;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 23;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 24;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 25;
UPDATE contract SET notes = 'Read the lease agreement thoroughly to understand the terms and conditions. Pay attention to the lease period, cancellation conditions, and other provisions.' WHERE contract_id = 26;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 27;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 28;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 29;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 30;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 31;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 32;
UPDATE contract SET notes = 'Check if the area is secure and close to amenities such as schools, hospitals, and stores.' WHERE contract_id = 33;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 34;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 35;
UPDATE contract SET notes = 'Determine the rental period and consider the possibility of extending the contract after that time.' WHERE contract_id = 36;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 37;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 38;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 39;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 40;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 41;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 42;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 43;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 44;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 45;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 46;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 47;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 48;
UPDATE contract SET notes = 'Determine the rental period and consider the possibility of extending the contract after that time.' WHERE contract_id = 49;
UPDATE contract SET notes = 'Check if the area is secure and close to amenities such as schools, hospitals, and stores.' WHERE contract_id = 50;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 51;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 52;
UPDATE contract SET notes = 'Check if the area is secure and close to amenities such as schools, hospitals, and stores.' WHERE contract_id = 53;
UPDATE contract SET notes = 'Read the lease agreement thoroughly to understand the terms and conditions. Pay attention to the lease period, cancellation conditions, and other provisions.' WHERE contract_id = 54;
UPDATE contract SET notes = 'Read the lease agreement thoroughly to understand the terms and conditions. Pay attention to the lease period, cancellation conditions, and other provisions.' WHERE contract_id = 55;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 56;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 57;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 58;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 59;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 60;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 61;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 62;
UPDATE contract SET notes = 'Determine the rental period and consider the possibility of extending the contract after that time.' WHERE contract_id = 63;
UPDATE contract SET notes = 'Understand the conditions and fees for canceling the lease before the agreed-upon term.' WHERE contract_id = 64;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 65;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 66;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 67;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 68;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 69;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 70;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 71;
UPDATE contract SET notes = 'Understand the conditions and fees for canceling the lease before the agreed-upon term.' WHERE contract_id = 72;
UPDATE contract SET notes = 'Read the lease agreement thoroughly to understand the terms and conditions. Pay attention to the lease period, cancellation conditions, and other provisions.' WHERE contract_id = 73;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 74;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 75;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 76;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 77;
UPDATE contract SET notes = 'Understand the conditions and fees for canceling the lease before the agreed-upon term.' WHERE contract_id = 78;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 79;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 80;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 81;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 82;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 83;
UPDATE contract SET notes = 'Inspect the condition of the house and available appliances to ensure they are in working order.' WHERE contract_id = 84;
UPDATE contract SET notes = 'If you have pets, check whether the landlord accepts pets.' WHERE contract_id = 85;
UPDATE contract SET notes = 'Understand the conditions and fees for canceling the lease before the agreed-upon term.' WHERE contract_id = 86;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 87;
UPDATE contract SET notes = 'Determine the rental period and consider the possibility of extending the contract after that time.' WHERE contract_id = 88;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 89;
UPDATE contract SET notes = 'Learn about maintenance and repair responsibilities between the landlord and the tenant.' WHERE contract_id = 90;
UPDATE contract SET notes = 'If necessary, consider purchasing home insurance to protect personal belongings.' WHERE contract_id = 91;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 92;
UPDATE contract SET notes = 'Clarify regulations regarding repairs and upgrades to the property.' WHERE contract_id = 93;
UPDATE contract SET notes = 'Read the lease agreement thoroughly to understand the terms and conditions. Pay attention to the lease period, cancellation conditions, and other provisions.' WHERE contract_id = 94;
UPDATE contract SET notes = 'Check if the area is secure and close to amenities such as schools, hospitals, and stores.' WHERE contract_id = 95;
UPDATE contract SET notes = 'Clearly establish the rental price and any additional costs such as service fees, water, electricity, and internet.' WHERE contract_id = 96;
UPDATE contract SET notes = 'If you have a car or motorcycle, inquire about parking availability and associated costs.' WHERE contract_id = 97;
UPDATE contract SET notes = 'Reach out to the property manager or landlord to ask questions and seek clarifications.' WHERE contract_id = 98;
UPDATE contract SET notes = 'Take note of the initial condition of the property and its assets when moving in to avoid disputes about damages when ending the lease.' WHERE contract_id = 99;
UPDATE contract SET notes = 'Determine the rental period and consider the possibility of extending the contract after that time.' WHERE contract_id = 100;


alter table report
modify column description varchar(1000);

alter table report
modify column name varchar(1000);

INSERT INTO report (report_id, name, description, status, create_by) VALUES
(1, 'Construction Disturbance', 'Description Noise from neighbors, especially during the night, can be bothersome', 'DISAPPROVED', 55),
(2, 'Construction Disturbance', 'Description Noise from construction or renovations within the apartment building', 'APPROVED', 17),  
(3, 'Regulation Inconsistencies', 'Description Inconsistencies in resident regulations and community management', 'DISAPPROVED', 71),  
(4, 'Common Area Maintenance', 'Description Issues related to maintaining common areas, parks, or shared facilities', 'APPROVED', 66),
(5, 'Security Concerns', 'Description Security concerns such as theft, bike theft, or accountability in case of security issues', 'APPROVED', 44),
(6, 'Management Fee Challenges', 'Description Management of the apartment building project can be challenging, especially concerning the collection of management fees and budget allocation', 'DISAPPROVED', 45),
(8, 'Cleanliness Matters', 'Description Issues related to apartment building cleanliness, including waste management and maintaining a clean surrounding area', 'APPROVED', 1),
(9, 'Technical Breakdowns', 'Description Breakdowns or technical issues within the apartment building, such as electrical or plumbing problems, elevator malfunctions, or safety system failures', 'DISAPPROVED', 39),
(10, 'Regulatory Compliance', 'Description Compliance with apartment building regulations and laws, including dispute resolution and conflicts', 'DISAPPROVED', 85),
(11, 'Traffic and Parking Issues', 'Description Issues related to traffic and parking within the apartment building area', 'APPROVED', 39),
(12, 'Community Participation', 'Description Interaction and participation of residents in community meetings', 'APPROVED', 83),       
(7, 'Common Area Maintenance', 'Description Maintenance of common areas such as elevators, hallways, and entryways to ensure safety and convenience', 'APPROVED', 76);