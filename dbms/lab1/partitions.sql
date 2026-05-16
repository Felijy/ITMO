CREATE TABLE olap_data (
    id serial,
    payload text,
    created_at timestamp default now()
) PARTITION BY RANGE (id);

CREATE TABLE olap_p1 PARTITION OF olap_data
    FOR VALUES FROM (0) TO (100000)
    TABLESPACE ts_onc39;

CREATE TABLE olap_p2 PARTITION OF olap_data
    FOR VALUES FROM (100000) TO (200000)
    TABLESPACE ts_ugp58;

INSERT INTO olap_data (payload) 
SELECT md5(generate_series(1, 199999)::text);

