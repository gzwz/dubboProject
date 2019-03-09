-- 获取两坐标点的距离（单位：米）
-- lng1 经度1
-- lat1 纬度1
-- lng2 经度2
-- lat2 纬度2
CREATE FUNCTION `get_distance` (
	lng1 DOUBLE,
	lat1 DOUBLE,
	lng2 DOUBLE,
	lat2 DOUBLE
) RETURNS DOUBLE
BEGIN
	RETURN ROUND(
		6371.393 * 2 * ASIN(
			SQRT(
				POW(
					SIN(
						(
							lat1 * PI() / 180 - lat2 * PI() / 180
						) / 2
					),
					2
				) + COS(lat1 * PI() / 180) * COS(lat2 * PI() / 180) * POW(
					SIN(
						(
							lng1 * PI() / 180 - lng2 * PI() / 180
						) / 2
					),
					2
				)
			)
		) * 1000
	);

end;