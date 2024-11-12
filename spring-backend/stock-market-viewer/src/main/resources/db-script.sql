DROP SCHEMA IF EXISTS cryptoDB CASCADE;
CREATE SCHEMA cryptoDB;

CREATE TABLE cryptoDB.trade_value (
      id SERIAL PRIMARY KEY,
      high_value DOUBLE PRECISION,
      mid_value DOUBLE PRECISION,
      low_value DOUBLE PRECISION,
      open_value DOUBLE PRECISION,
      close_value DOUBLE PRECISION
);

CREATE TABLE cryptoDB.volume (
     id SERIAL PRIMARY KEY,
     volume DOUBLE PRECISION,
     volume_notional DOUBLE PRECISION,
     trades_done DOUBLE PRECISION
);

CREATE TABLE cryptoDB.exchange_info (
    id SERIAL PRIMARY KEY,
    trade_values_id INT REFERENCES cryptoDB.trade_value(id),
    volume_id INT REFERENCES cryptoDB.volume(id),
    ticker varchar,
    trade_time TIMESTAMP
);

SELECT tv.*
FROM cryptodb.trade_value tv
         LEFT JOIN cryptodb.exchange_info ei ON ei.id = tv.id
WHERE ei.trade_time > CURRENT_TIMESTAMP;




