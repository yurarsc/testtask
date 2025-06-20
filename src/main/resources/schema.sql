CREATE TABLE IF NOT EXISTS public.user (
	id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	name varchar(500) NOT NULL,
	date_of_birth date NOt NULL,
	password varchar(500) NOT NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.account (
	id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	user_id int8 NOT NULL REFERENCES public.user(id),
	balance decimal NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id),
	CONSTRAINT balance_positive check (balance >= 0)
);

CREATE TABLE IF NOT EXISTS public.email_data (
	id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	user_id int8 NOT NULL REFERENCES public.user(id),
	email varchar(200) NOT NULL,
	CONSTRAINT email_data_pkey PRIMARY KEY (id),
	CONSTRAINT email_data_unique UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.phone_data (
	id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	user_id int8 NOT NULL REFERENCES public.user(id),
	phone varchar(13) NOT NULL,
	CONSTRAINT phone_data_pkey PRIMARY KEY (id),
	CONSTRAINT phone_data_unique UNIQUE (phone)
);


CREATE OR REPLACE PROCEDURE UPDATE_BALANCE_MAX(IN ACCOUNT_ID BIGINT,
									           IN MAX_VALUE DOUBLE PRECISION)
LANGUAGE plpgsql AS
'
DECLARE
	VALUE DECIMAL;
BEGIN
	SELECT balance
	INTO VALUE
	FROM account
	WHERE id = ACCOUNT_ID
	FOR UPDATE;

	VALUE = LEAST(VALUE*1.1, MAX_VALUE);

	UPDATE account
	SET balance = VALUE
	WHERE id = ACCOUNT_ID;
END
';


CREATE OR REPLACE PROCEDURE ACCOUNT_TRANSFER(IN USER_ID_FROM BIGINT,
											 in USER_ID_TO BIGINT,
									         IN AMOUNT DOUBLE PRECISION)
LANGUAGE plpgsql AS
'
DECLARE VALUE_FROM decimal;
		VALUE_TO decimal;
		ACCOUNT_ID BIGINT;
BEGIN
	SELECT balance
	INTO VALUE_FROM
	FROM account
	WHERE user_id = USER_ID_FROM;

	IF VALUE_FROM < AMOUNT THEN
		RAISE EXCEPTION ''account balance % not enough for transfer: %'', VALUE_FROM, AMOUNT;
	END IF;

	UPDATE account
	SET balance = VALUE_FROM - AMOUNT
	WHERE USER_ID = USER_ID_FROM;

	SELECT id
	INTO account_id
	FROM account
	WHERE user_id = USER_ID_TO;

	IF ACCOUNT_ID IS NULL THEN
		RAISE EXCEPTION ''destination account not found for: %'', USER_ID_TO;
	END IF;

	SELECT balance
	INTO VALUE_TO
	FROM account
	WHERE user_id = USER_ID_TO;

	UPDATE account
	SET balance = VALUE_TO + AMOUNT
	WHERE USER_ID = USER_ID_TO;
END
';

