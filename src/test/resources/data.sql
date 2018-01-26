INSERT INTO `gateway` (`ipv4_address`, `name`, `serial_number`)
VALUES ('255.168.3.24', 'testOne', '1111-2222-3333-4444-5555');
INSERT INTO `gateway` (`ipv4_address`, `name`, `serial_number`)
VALUES ('255.168.3.34', 'testTwo', '0000-2222-3333-4444-5555');
INSERT INTO `peripheral_device` (`date_created`, `status`, `uid`, `vendor`, `gateway_id`)
VALUES ('2018-01-25 14:08:48', 'OFFLINE', '1', 'IBM', '1');
INSERT INTO `peripheral_device` (`date_created`, `status`, `uid`, `vendor`, `gateway_id`)
VALUES ('2018-01-25 14:08:48', 'ONLINE', '2', 'INTEL', '2');