SELECT c.name AS name, MONTH(bs.date) AS MONTH, SUM(bd.quantity) AS quantity FROM bill_details AS bd
JOIN products AS p
	JOIN categories AS c
		JOIN category_groups AS cg ON cg.id = c.categoryGroupId
	ON c.id = p.categoryId
ON p.id = bd.productId
JOIN bill_statuses AS bs ON bs.billId = bd.billId
WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
AND YEAR(bs.date) = 2023
GROUP BY c.name, MONTH(bs.date);

SELECT 
	cg.name AS name,
	CASE
		WHEN MONTH(bs.date) IN (1, 2, 3) THEN 1
		WHEN MONTH(bs.date) IN (4, 5, 6) THEN 2
		WHEN MONTH(bs.date) IN (7, 8, 9) THEN 3
		ELSE 4
	END AS month, 
	SUM(bd.quantity) AS quantity 
FROM bill_details AS bd
JOIN products AS p
	JOIN categories AS c
		JOIN category_groups AS cg ON cg.id = c.categoryGroupId
	ON c.id = p.categoryId
ON p.id = bd.productId
JOIN bill_statuses AS bs ON bs.billId = bd.billId
WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
AND YEAR(bs.date) = 2023
GROUP BY cg.name, MONTH(bs.date) IN (1, 2, 3), MONTH(bs.date) IN (4, 5, 6), MONTH(bs.date) IN (7, 8, 9), MONTH(bs.date) IN (10, 11, 12);

SELECT cg.name FROM category_groups AS cg

SELECT p.id, COUNT(*) FROM products AS p
JOIN product_images AS pim ON pim.productId = p.id
GROUP BY p.id
HAVING COUNT(*) <= 1

SELECT * FROM product_images AS pimg
WHERE pimg.productId = 93

SELECT * FROM bill_details AS bd
WHERE bd.productId = 100;

SELECT 
	p.name AS name,
	DAY(bs.date) AS month, 
	SUM(bd.quantity) AS quantity 
FROM bill_details AS bd
JOIN products AS p ON p.id = bd.productId
JOIN bill_statuses AS bs ON bs.billId = bd.billId
WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
AND YEAR(bs.date) = 2023 AND MONTH (bs.date) = 1  AND p.id = 1
GROUP BY p.name, DAY(bs.date);

SELECT p.id, p.name, YEAR(bs.date), MONTH(bs.date), DAY(bs.date)
FROM bills AS b
JOIN bill_details AS bd
	JOIN products AS p ON p.id = bd.id
ON bd.billId = b.id
JOIN bill_statuses AS bs ON bs.billId = b.id
WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = b.id)
AND MONTH(bs.date) = 1
ORDER BY p.id ASC

SELECT p.name AS name, DAY(bs.date) AS TIME, SUM(bd.quantity) AS quantity
FROM bills AS b
JOIN bill_details AS bd
	JOIN products AS p ON p.id = bd.id
ON bd.billId = b.id
JOIN bill_statuses AS bs ON bs.billId = b.id
WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = b.id)
AND YEAR(bs.date) = 2023 AND MONTH(bs.date) = 1 AND p.id = 15
GROUP BY p.name, DAY(bs.date)

SELECT bi.urlImage, bi.id, bi.description FROM banner_images bi WHERE bi.description LIKE 'slide%'

SELECT b.id AS billId, b.userId AS userId, bs.`status` AS status, b.userName AS name, b.email AS email, b.transfer AS transfer
FROM bills AS b
JOIN bill_details AS bd ON bd.billId = b.id
JOIN bill_statuses AS bs ON bs.billId = b.id
WHERE bs.id = (SELECT MAX(bill_statuses.id) FROM bill_statuses WHERE bill_statuses.billId = b.id)
AND b.userName LIKE '%%'
AND CAST(bs.id AS CHAR(11)) LIKE '%1%'
LIMIT 7 OFFSET 0;

SELECT bs.billId, bs.date, bs.`status`
FROM bill_statuses AS bs
WHERE bs.`status` LIKE '%%';

SELECT bs.date
FROM bill_statuses AS bs
WHERE bs.id = :billId

SELECT COUNT(*)
FROM bills AS b
JOIN bill_details AS bd ON bd.billId = b.id
JOIN bill_statuses AS bs ON bs.billId = b.id
WHERE bs.id = (SELECT MAX(bill_statuses.id) FROM bill_statuses WHERE bill_statuses.billId = b.id)


DELETE FROM users WHERE id > 4