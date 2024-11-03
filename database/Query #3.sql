SELECT SUM(bd.quantity)
FROM bill_details AS bd
LEFT JOIN products AS P
	JOIN categories AS c 
		JOIN category_groups AS cg ON cg.id = c.categoryGroupId
	ON c.id = p.categoryId
ON p.id = bd.productId
JOIN bills AS b 
	JOIN bill_statuses AS bs ON bs.billId = b.id
ON b.id = bd.billId
GROUP BY c.id AND YEAR(b.);

SELECT p. FROM products AS p
JOIN category_groups AS cg ON p.categoryId;

SELECT * FROM dia_chi.wards AS w
WHERE w.districtCode ='596'

INSERT INTO `mat_kinh_kimi`.`bills` (`id`, `userId`, `userName`, `email`, `phoneNumber`, `codeProvince`, `codeDistrict`, `codeWard`, `address`, `transportFee`, `transfer`) VALUES (2, 1, 'Nguyễn Đình Lam', 'kiminonawa1305@gmail.com', '0855354919', '60', '596', '23044', '221 - Bình Đức - Phan Hiệp - Bắc bình - Bình Thuận', 20000, b'0');
SELECT `id`, `userId`, `userName`, `email`, `phoneNumber`, `codeProvince`, `codeDistrict`, `codeWard`, `address`, `transportFee`, `transfer` FROM `mat_kinh_kimi`.`bills` WHERE  `id`=2;

SELECT id FROM models as m WHERE m.productId = 2


SELECT cg.name, MONTH(bs.date), SUM(bd.quantity) FROM bill_details AS bd
JOIN products AS p 
	JOIN categories AS c
		JOIN category_groups AS cg ON cg.id = c.categoryGroupId
	ON c.id = p.categoryId
ON p.id = bd.productId
JOIN bill_statuses AS bs ON bs.billId = bd.billId
WHERE bs.id = (SELECT MIN(id) FROM bill_statuses WHERE bill_statuses.billId = bd.billId)
AND YEAR(bs.date) = 2023 AND MONTH(bs.date) IN (1, 2, 3)
GROUP BY cg.name;

