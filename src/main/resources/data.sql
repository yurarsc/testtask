INSERT INTO public.user(id, name, date_of_birth, password) VALUES(1, 'Alex', '01.01.1990', 'secret')
INSERT INTO public.user(id, name, date_of_birth, password) VALUES(2, 'Jane', '01.01.1991', 'secret')

INSERT INTO public.account(user_id, ballance) VALUES(1, 100)
INSERT INTO public.account(user_id, ballance) VALUES(2, 120)

INSERT INTO public.email_data(user_id, email) VALUES(1, 'alex@example.com')
INSERT INTO public.email_data(user_id, email) VALUES(2, 'jane@example.com')

INSERT INTO public.phone_data(user_id, phone) VALUES(1, '79207865432')
INSERT INTO public.phone_data(user_id, phone) VALUES(2, '12345678901')