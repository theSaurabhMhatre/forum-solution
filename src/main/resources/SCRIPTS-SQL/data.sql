--inserting dummy data in user table
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (1, 'one', 'YjI1bGNBPT0=', 'one@mail.com', 'I am user one. I have got a lot of swag.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (2, 'two', 'ZEhkdmNBPT0=', 'two@mail.com', 'I am user two. I like to play cricket.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (3, 'three', 'ZEdoeVpXVnc=', 'three@mail.com', 'I am user three. I like to ask questions.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (4, 'four', 'Wm05MWNuQT0=', 'four@mail.com', 'I am user four. I am cool, like really cool.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (5, 'five', 'Wm1sMlpYQT0=', 'five@mail.com', 'I am user five. Bring them on.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (6, 'six', 'YzJsNGNBPT0=', 'six@mail.com', 'I am user six. Lets answer some questions.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (7, 'seven', 'YzJWMlpXNXc=', 'seven@mail.com', 'I am user seven. I am a genius.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (8, 'eight', 'WldsbmFIUnc=', 'eight@mail.com', 'I am use eight. Let us all chill down.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (9, 'nine', 'Ym1sdVpYQT0=', 'nine@mail.com', 'I am user nine. I am an avid reader.',sysdate, sysdate);
insert into table_user (user_id, user_name, user_pswd, user_mail, user_bio, user_created_on, user_modified_on) values (10, 'ten', 'ZEdWdWNBPT0=', 'ten@mail.com', 'I am user ten. I am the last user.',sysdate, sysdate);

--inserting dummy data in question table
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (1, 'What is question one?', 'science', 1, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (2, 'What is question two?', 'history', 2, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (3, 'What is question three?', 'technology', 3, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (4, 'What is question four?', 'technology', 4, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (5, 'What is question five?', 'history', 4, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (6, 'What is question six?', 'science', 8, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (7, 'What is question seven?', 'science', 2, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (8, 'What is question eight?', 'history', 1, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (9, 'What is question nine?', 'technology', 4, sysdate, sysdate);
insert into table_question (ques_id, ques_str, ques_category, asked_by, ques_created_on, ques_modified_on) values (10, 'What is question ten?', 'science', 3, sysdate, sysdate);

--inserting dummy data in answer table
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (1, 'This is answer one. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 1, 1, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (2, 'This is answer two. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 1, 2, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (3, 'This is answer three. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 1, 3, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (4, 'This is answer four. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 1, 4, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (5, 'This is answer five. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 2, 1, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (6, 'This is answer six. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 2, 2, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (7, 'This is answer seven. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 2, 3, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (8, 'This is answer eight. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 3, 4, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (9, 'This is answer nine. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 5, 6, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (10, 'This is answer ten. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 5, 7, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (11, 'This is answer eleven. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 6, 6, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (12, 'This is answer twelve. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 6, 7, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (13, 'This is answer thirteen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 7, 6, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (14, 'This is answer fourteen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 7, 7, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (15, 'This is answer fifteen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 3, 6, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (16, 'This is answer sixteen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 1, 8, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (17, 'This is answer seventeen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 5, 10, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (18, 'This is answer eighteen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 6, 9, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (19, 'This is answer nineteen. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 9, 9, sysdate, sysdate);
insert into table_answer (ans_id, ans_str, ques_id ,answered_by, ans_created_on, ans_modified_on) values (20, 'This is answer twenty. A clone is a copy of a repository that lives on your computer instead of on a websites server somewhere, or the act of making that copy. With your clone you can edit the files in your preferred editor and use Git to keep track of your changes without having to be online. It is, however, connected to the remote version so that changes can be synced between the two. You can push your local changes to the remote to keep them synced when youre online.', 10, 10, sysdate, sysdate);

--inserting dummy data in question like table
insert into table_question_like (ques_id, user_id, ques_liked_on) values (1, 2, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (2, 2, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (1, 1, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (2, 1, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (2, 3, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (2, 4, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (3, 1, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (3, 2, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (3, 3, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (3, 4, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (4, 3, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (5, 4, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (6, 1, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (6, 2, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (6, 3, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (7, 6, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (7, 7, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (7, 8, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (8, 4, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (8, 5, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (8, 6, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (9, 7, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (10, 8, sysdate);
insert into table_question_like (ques_id, user_id, ques_liked_on) values (10, 9, sysdate);

--inserting dummy data in answer like table
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (1, 1, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (2, 1, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (16, 1, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (1, 1, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (2, 1, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (3, 1, 3, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (1, 1, 5, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (4, 1, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (16, 1, 9, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (5, 2, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (6, 2, 4, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (7, 2, 5, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (6, 2, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (7, 2, 9, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (6, 2, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (6, 2, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (6, 2, 10, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (8, 3, 9, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (8, 3, 10, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (8, 3, 4, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (8, 3, 3, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (15, 3, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (15, 3, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (15, 3, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (9, 5, 6, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (9, 5, 7, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (10, 5, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (10, 5, 4, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (10, 5, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (17, 5, 10, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (11, 6, 4, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (11, 6, 5, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (11, 6, 6, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (11, 6, 7, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (11, 6, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (12, 6, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (12, 6, 9, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (12, 6, 10, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (18, 6, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (18, 6, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (18, 6, 6, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (13, 7, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (13, 7, 2, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (13, 7, 3, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (14, 7, 6, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (14, 7, 7, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (14, 7, 8, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (19, 9, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (19, 9, 9, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (20, 10, 1, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (20, 10, 9, sysdate);
insert into table_answer_like (ans_id, ques_id, user_id, ans_liked_on) values (20, 10, 4, sysdate);

--removing data from tables
delete from table_answer_like;
delete from table_question_like;
delete from table_answer;
delete from table_question;
delete from table_user;

--selecting data from tables
select * from table_user;
select * from table_question;
select * from table_answer;
select * from table_question_like;
select * from table_answer_like;

--dropping tables
drop table table_user;
drop table table_question;
drop table table_answer;
drop table table_question_like;
drop table table_answer_like;

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

--search queries
select * from table_question
where ques_str like '%test%'
and ques_category like 'history'
order by ques_id;

select count(*) as likes, quesLike.ques_id
from table_question_like quesLike, table_question ques
where quesLike.ques_id = ques.ques_id 
and ques.ques_str like '%%'
and ques.ques_category like '%%'
group by quesLike.ques_id
order by likes;

select ans.* from table_answer ans, table_question ques 
where ans.ques_id = ques.ques_id
and ans.ans_str like '%test%'
and ques.ques_category like '%%';

select count(*) as likes, ans_id 
from table_answer_like
where ans_id in
(
select ans.ans_id from table_answer ans, table_question ques 
where ans.ques_id = ques.ques_id
and ans.ans_str like '%demo%'
and ques.ques_category like '%%'
)
group by ans_id;