INSERT INTO hub VALUES('06:38:35:3d:4d:fc', '1.0', '1.0', '1.0');
INSERT INTO hub VALUES('00:38:35:3d:4d:fc', '1.0', '1.0', '2.0');
INSERT INTO hub VALUES('06:89:00:3d:4d:fc', '1.0', '1.0', '3.0');
INSERT INTO hub VALUES('10:89:00:3d:4d:fc', '1.0', '1.0', '1.0');
INSERT INTO hub VALUES('2e:89:00:3d:4d:fc', '1.0', '1.0', '1.0');


INSERT INTO phone VALUES('356938035643809', '06:38:35:3d:4d:fc', 'S820', 'Lenovo', 'Android', '4.1');
INSERT INTO phone VALUES('126938035643809', '06:38:35:3d:4d:fc', 'S80', 'Lenovo', 'Android', '4.1');
INSERT INTO phone VALUES('726938035643809', '00:38:35:3d:4d:fc', 'S5', 'Samsung', 'Android', '4.1');
INSERT INTO phone VALUES('226938035643809', '00:38:35:3d:4d:fc', 'S3', 'Samsung', 'Android', '4.1');
INSERT INTO phone VALUES('626938035643809', '06:89:00:3d:4d:fc', '3', 'Xiaomi', 'Android', '4.1');
INSERT INTO phone VALUES('926938035643809', '06:89:00:3d:4d:fc', '2', 'Xiaomi', 'Android', '4.1');
INSERT INTO phone VALUES('123938035643809', '06:89:00:3d:4d:fc', '2', 'Xiaomi', 'Android', '4.1');

INSERT INTO gadget VALUES('06:23:00:3d:4d:fc', '06:38:35:3d:4d:fc', 'switch', '客厅灯', 'added');
INSERT INTO gadget VALUES('16:23:10:3d:4d:fc', '06:38:35:3d:4d:fc', 'switch', '厕所灯', 'added');
INSERT INTO gadget VALUES('26:23:20:3d:4d:fc', '00:38:35:3d:4d:fc', 'lock', '家门', 'deleted');
INSERT INTO gadget VALUES('36:23:30:3d:4d:fc', '00:38:35:3d:4d:fc', 'lock', '后门', 'added');
INSERT INTO gadget VALUES('46:23:40:3d:4d:fc', '06:89:00:3d:4d:fc', 'humidifier', '卧室', 'na');
INSERT INTO gadget VALUES('56:23:50:3d:4d:fc', '06:89:00:3d:4d:fc', 'humidifier', '客厅', 'added');
INSERT INTO gadget VALUES('66:23:50:3d:4d:fc', '10:89:00:3d:4d:fc', 'switch', '客厅灯', 'added');
INSERT INTO gadget VALUES('76:23:50:3d:4d:fc', '06:89:00:3d:4d:fc', 'switch', '客厅灯', 'added');
INSERT INTO gadget VALUES('96:23:50:3d:4d:fc', '10:89:00:3d:4d:fc', 'lock', '家门', 'added');
INSERT INTO gadget VALUES('86:23:50:3d:4d:fc', '2e:89:00:3d:4d:fc', 'humidifier', '卧室', 'added');

INSERT INTO hub_log VALUES(DEFAULT,now(),'query', 'WIFI', '06:38:35:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'connect', 'WIFI', '06:38:35:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'scan', 'WIFI', '00:38:35:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'control', 'WIFI', '00:38:35:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'control', 'WIFI', '06:89:00:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'test', 'WIFI', '06:89:00:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'control', 'WIFI', '10:89:00:3d:4d:fc');
INSERT INTO hub_log VALUES(DEFAULT,now(),'control', 'WIFI', '2e:89:00:3d:4d:fc');

INSERT INTO gadget_log VALUES(DEFAULT,now(),'on','WIFI','356938035643809','P','06:23:00:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'off','WIFI','356938035643809','P','06:23:00:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'on','WIFI','356938035643809','P','16:23:10:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'off','WIFI','356938035643809','P','16:23:10:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'lock','WIFI','926938035643809','P','26:23:20:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'unlock','WIFI','926938035643809','P','26:23:20:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'reset','WIFI','926938035643809','P','36:23:30:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'debug','WIFI','926938035643809','P','36:23:30:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'lock','WIFI','926938035643809','P','36:23:30:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'lock','WIFI','926938035643809','P','36:23:30:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'on','WIFI','926938035643809','P','46:23:40:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'on','WIFI','926938035643809','P','46:23:40:3d:4d:fc');
INSERT INTO gadget_log VALUES(DEFAULT,now(),'off','WIFI','926938035643809','P','46:23:40:3d:4d:fc');
