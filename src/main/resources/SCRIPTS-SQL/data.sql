--inserting dummy data in user table
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (1, 'one', 'YjI1bGNBPT0=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (2, 'two', 'ZEhkdmNBPT0=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (3, 'three', 'ZEdoeVpXVnc=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (4, 'four', 'Wm05MWNuQT0=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (5, 'five', 'Wm1sMlpYQT0=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (6, 'six', 'YzJsNGNBPT0=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (7, 'seven', 'YzJWMlpXNXc=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (8, 'eight', 'WldsbmFIUnc=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (9, 'nine', 'Ym1sdVpYQT0=', 'mail', 'bio');
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio) values (10, 'ten', 'ZEdWdWNBPT0=', 'mail', 'bio');

--inserting dummy data in question table
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (1, 'what is question one?', 'science', 1);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (2, 'what is question two?', 'history', 2);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (3, 'what is question three?', 'technology', 3);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (4, 'what is question four?', 'technology', 4);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (5, 'what is question five?', 'history', 4);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (6, 'what is question six?', 'science', 8);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (7, 'what is question seven?', 'science', 2);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (8, 'what is question eight?', 'history', 1);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (9, 'what is question nine?', 'technology', 4);
insert into table_question (ques_id, ques_str, ques_category, asked_by) values (10, 'what is question ten?', 'science', 3);

--inserting dummy data in answer table
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (1, 'this is answer one', 1, 1);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (2, 'this is answer two', 1, 2);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (3, 'this is answer three', 1, 3);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (4, 'this is answer four', 1, 4);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (5, 'this is answer five', 2, 1);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (6, 'this is answer six', 2, 2);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (7, 'this is answer seven', 2, 3);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (8, 'this is answer eight', 3, 4);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (9, 'this is answer nine', 5, 6);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (10, 'this is answer ten', 5, 7);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (11, 'this is answer eleven', 6, 6);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (12, 'this is answer twelve', 6, 7);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (13, 'this is answer thirteen', 7, 6);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (14, 'this is answer fourteen', 7, 7);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (15, 'this is answer fifteen', 3, 6);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (16, 'this is answer sixteen', 1, 8);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (17, 'this is answer seventeen', 5, 10);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (18, 'this is answer eighteen', 6, 9);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (19, 'this is answer nineteen', 9, 9);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by) values (20, 'this is answer twenty', 10, 10);

--inserting dummy data in question like table
insert into table_question_like (ques_id, user_id) values (1, 2);
insert into table_question_like (ques_id, user_id) values (2, 2);
insert into table_question_like (ques_id, user_id) values (1, 1);
insert into table_question_like (ques_id, user_id) values (2, 1);
insert into table_question_like (ques_id, user_id) values (2, 3);
insert into table_question_like (ques_id, user_id) values (2, 4);
insert into table_question_like (ques_id, user_id) values (3, 1);
insert into table_question_like (ques_id, user_id) values (3, 2);
insert into table_question_like (ques_id, user_id) values (3, 3);
insert into table_question_like (ques_id, user_id) values (3, 4);
insert into table_question_like (ques_id, user_id) values (4, 3);
insert into table_question_like (ques_id, user_id) values (5, 4);
insert into table_question_like (ques_id, user_id) values (6, 1);
insert into table_question_like (ques_id, user_id) values (6, 2);
insert into table_question_like (ques_id, user_id) values (6, 3);
insert into table_question_like (ques_id, user_id) values (7, 6);
insert into table_question_like (ques_id, user_id) values (7, 7);
insert into table_question_like (ques_id, user_id) values (7, 8);
insert into table_question_like (ques_id, user_id) values (8, 4);
insert into table_question_like (ques_id, user_id) values (8, 5);
insert into table_question_like (ques_id, user_id) values (8, 6);
insert into table_question_like (ques_id, user_id) values (9, 7);
insert into table_question_like (ques_id, user_id) values (10, 8);
insert into table_question_like (ques_id, user_id) values (10, 9);

--inserting dummy data in answer like table
insert into table_answer_like (ans_id, ques_id, user_id) values (1, 1, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (2, 1, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (16, 1, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (1, 1, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (2, 1, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (3, 1, 3);
insert into table_answer_like (ans_id, ques_id, user_id) values (1, 1, 5);
insert into table_answer_like (ans_id, ques_id, user_id) values (4, 1, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (16, 1, 9);
insert into table_answer_like (ans_id, ques_id, user_id) values (5, 2, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (6, 2, 4);
insert into table_answer_like (ans_id, ques_id, user_id) values (7, 2, 5);
insert into table_answer_like (ans_id, ques_id, user_id) values (6, 2, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (7, 2, 9);
insert into table_answer_like (ans_id, ques_id, user_id) values (6, 2, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (6, 2, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (6, 2, 10);
insert into table_answer_like (ans_id, ques_id, user_id) values (8, 3, 9);
insert into table_answer_like (ans_id, ques_id, user_id) values (8, 3, 10);
insert into table_answer_like (ans_id, ques_id, user_id) values (8, 3, 4);
insert into table_answer_like (ans_id, ques_id, user_id) values (8, 3, 3);
insert into table_answer_like (ans_id, ques_id, user_id) values (15, 3, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (15, 3, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (15, 3, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (9, 5, 6);
insert into table_answer_like (ans_id, ques_id, user_id) values (9, 5, 7);
insert into table_answer_like (ans_id, ques_id, user_id) values (10, 5, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (10, 5, 4);
insert into table_answer_like (ans_id, ques_id, user_id) values (10, 5, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (17, 5, 10);
insert into table_answer_like (ans_id, ques_id, user_id) values (11, 6, 4);
insert into table_answer_like (ans_id, ques_id, user_id) values (11, 6, 5);
insert into table_answer_like (ans_id, ques_id, user_id) values (11, 6, 6);
insert into table_answer_like (ans_id, ques_id, user_id) values (11, 6, 7);
insert into table_answer_like (ans_id, ques_id, user_id) values (11, 6, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (12, 6, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (12, 6, 9);
insert into table_answer_like (ans_id, ques_id, user_id) values (12, 6, 10);
insert into table_answer_like (ans_id, ques_id, user_id) values (18, 6, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (18, 6, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (18, 6, 6);
insert into table_answer_like (ans_id, ques_id, user_id) values (13, 7, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (13, 7, 2);
insert into table_answer_like (ans_id, ques_id, user_id) values (13, 7, 3);
insert into table_answer_like (ans_id, ques_id, user_id) values (14, 7, 6);
insert into table_answer_like (ans_id, ques_id, user_id) values (14, 7, 7);
insert into table_answer_like (ans_id, ques_id, user_id) values (14, 7, 8);
insert into table_answer_like (ans_id, ques_id, user_id) values (19, 9, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (19, 9, 9);
insert into table_answer_like (ans_id, ques_id, user_id) values (20, 10, 1);
insert into table_answer_like (ans_id, ques_id, user_id) values (20, 10, 9);
insert into table_answer_like (ans_id, ques_id, user_id) values (20, 10, 4);

--removing data from tables
delete from table_user;
delete from table_question;
delete from table_answer;
delete from table_question_like;
delete from table_answer_like;

--selecting data from tables
select * from table_user;
select * from table_question;
select * from table_answer;
select * from table_question_like;
select * from table_answer_like;

--selecting questions ordered by likes
select ques_id, count(*) as x
from table_question_like
group by ques_id
order by x desc;

--selecting answers for a particular question ordered by likes
select a.ans_id, count(*) as x 
from table_answer_like a inner join table_answer b 
on a.ans_id = b.ans_id 
where ques_id = 1 
group by a.ans_id 
order by x desc;

--back up
select ans_id, ques_id, count(*) as x
from table_answer_like
group by ques_id, ans_id
order by ques_id, x desc;

--selecting top answer for every question
select second.ans_id, second.ques_id, second.x as likes
from
(
select ques_id, max(x) as y
from
(
select ans_id, ques_id, count(*) as x
from table_answer_like
group by ques_id, ans_id
order by ques_id, x desc
)
group by ques_id
) as first,
(
select ans_id, ques_id, count(*) as x
from table_answer_like
group by ques_id, ans_id
order by ques_id, x desc
) as second
where first.ques_id = second.ques_id
and first.y = second.x;

--fetch answer by ques id
select ans_id from table_answer
where ques_id = 3
limit 1;

--fetch likes for a particular question
select count(*) from table_question_like
where ques_id = 1;

--cumulative likes per user
select aLikes, qLikes, (aLikes + qLikes) as total from
(
select count(*) as aLikes from table_answer ans, table_answer_like likes
where ans.ans_id = likes.ans_id and
ans.answered_by = 1
),
(
select count(*) as qLikes from table_question ques, table_question_like likes
where ques.ques_id = likes.ques_id and
ques.asked_by = 1
);

--cumulative ans likes by users
select count(*) as total, x.user_id from table_answer ans, table_answer_like likes, table_user x
where ans.ans_id = likes.ans_id
and ans.answered_by = x.user_id
group by x.user_id
order by x.user_id;

--cumulative ques likes by users
select count(*), x.user_id from table_question ques, table_question_like likes, table_user x
where ques.ques_id = likes.ques_id
and ques.asked_by = x.user_id
group by x.user_id
order by x.user_id;