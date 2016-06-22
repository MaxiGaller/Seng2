CREATE TABLE M_USER (ID INTEGER IDENTITY, muname VARCHAR(255) UNIQUE, mpwd VARCHAR(255), salt VARCHAR(255));
CREATE TABLE NEWSLETTER (id NUMERIC PRIMARY KEY, text VARCHAR(1000));
CREATE TABLE M_ADMIN (ID NUMERIC PRIMARY KEY, mpwd VARCHAR(255) UNIQUE);

CREATE TABLE LatexSniped (id NUMERIC IDENTITY, muser_id NUMERIC, document_id NUMERIC, ordinal int not null, content VARCHAR(1000000), content_type NUMERIC, global_Sniped_id NUMERIC, editable NUMERIC, trash NUMERIC);
CREATE TABLE LatexGlobalSniped (id NUMERIC IDENTITY, muser_id NUMERIC, content VARCHAR(1000000), content_type NUMERIC);
CREATE TABLE LatexDocuments (id NUMERIC IDENTITY, muser_id NUMERIC, documentname VARCHAR(255), documentauthor VARCHAR(999), trash NUMERIC);
CREATE TABLE LatexType (id NUMERIC IDENTITY, type VARCHAR(255), type_opening_tag VARCHAR(255), type_closeing_tag VARCHAR(255), html_opening_tag VARCHAR(255), html_closeing_tag VARCHAR(255), accessable NUMERIC);
CREATE TABLE LatexDocumentContributors (id NUMERIC IDENTITY, owner_muser_id NUMERIC, contribute_muser_id NUMERIC, document_id NUMERIC);
CREATE UNIQUE INDEX duplicate_DocumentContributors ON LatexDocumentContributors (contribute_muser_id, document_id);

INSERT INTO LatexDocumentContributors (id, owner_muser_id, contribute_muser_id, document_id) VALUES (NULL, '2', '3', '0');
INSERT INTO LatexDocumentContributors (id, owner_muser_id, contribute_muser_id, document_id) VALUES (NULL, '3', '2', '3');

INSERT INTO LatexDocuments (id, muser_id, documentname, documentauthor, trash) VALUES (NULL, 2, 'Test Doc', 'q', 0);
INSERT INTO LatexDocuments (id, muser_id, documentname, documentauthor, trash) VALUES (NULL, 2, 'Ernsthaftes Test Dokument', 'q', 0);
INSERT INTO LatexDocuments (id, muser_id, documentname, documentauthor, trash) VALUES (NULL, 2, 'LULULULULUL', 'q', 1);
INSERT INTO LatexDocuments (id, muser_id, documentname, documentauthor, trash) VALUES (NULL, 3, 'Contribute Dokument', 'Franz', 0);

insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 0, 0, 'Bla Bla Blub! Überschrift', 0, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 0, 1, 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum', 3, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 0, 2, 'Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet ', 1, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 0, 3, 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum', 3, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 0, 4, 'Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet Dolor Sit Amet ', 2, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 0, 5, 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem IpsumLorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem IpsumLorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum', 3, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 0, 'H1 H1 H1 H1 H1', 0, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 1, 'TEXT TEXT TEXT', 3, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 2, 'H2 H2 H2 H2 H2', 1, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 3, 'TEXT TEXT TEXT', 3, NULL, 1, 0);
insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 4, 'H3 H3 H3 H3 H3', 2, NULL, 1, 0);
--insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 5, 'DU DARFST MICH NICHT SEHEN', 5, 1, 0, 0);
--insert into LatexSniped (id, muser_id, document_id, ordinal, content, content_type, global_Sniped_id, editable, trash) VALUES (NULL, 2, 1, 6, 'DU DARFST MICH NICHT SEHEN', 5, 2, 0, 0);

insert into LatexGlobalSniped (id, muser_id, content, content_type) VALUES (NULL, 0, 'NULL', NULL);
insert into LatexGlobalSniped (id, muser_id, content, content_type) VALUES (NULL, 2, 'Globaler Footer', 3);
insert into LatexGlobalSniped (id, muser_id, content, content_type) VALUES (NULL, 2, 'Fetter Text der überall krass wichtig is!', 3);

insert into LatexType (id, type, type_opening_tag, type_closeing_tag, html_opening_tag, html_closeing_tag, accessable) VALUES (NULL, 'section', '\section{', '}', '<h1>', '</h1>', 1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, html_opening_tag, html_closeing_tag, accessable) VALUES (NULL, 'subsection', '\subsection{', '}', '<h2>', '</h2>',  1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, html_opening_tag, html_closeing_tag, accessable) VALUES (NULL, 'subsubsection', '\subsubsection{', '}', '<h3>', '</h3>',  1);
insert into LatexType (id, type, type_opening_tag, type_closeing_tag, html_opening_tag, html_closeing_tag, accessable) VALUES (NULL, 'text', '', '', '<p>', '</p>',  1);



insert into M_USER (ID,muname,mpwd) values (0,'never','login');
insert into M_USER (ID,muname,mpwd, salt) values (1,'Hias','31205d955e9483a93461b57888fd3a8c2a417b53bbc2def17ce18697f719473f', '1c0qjj3cna3lmuee5ocsjp1sb6ghmk6t65sl3qvqu1pjopotb51n');
insert into M_USER (ID,muname,mpwd, salt) values (2,'q','31205d955e9483a93461b57888fd3a8c2a417b53bbc2def17ce18697f719473f', '1c0qjj3cna3lmuee5ocsjp1sb6ghmk6t65sl3qvqu1pjopotb51n');
insert into M_USER (ID,muname,mpwd, salt) values (3,'Franz','31205d955e9483a93461b57888fd3a8c2a417b53bbc2def17ce18697f719473f', '1c0qjj3cna3lmuee5ocsjp1sb6ghmk6t65sl3qvqu1pjopotb51n');
insert into M_USER (ID,muname,mpwd, salt) values (4,'Sepp','31205d955e9483a93461b57888fd3a8c2a417b53bbc2def17ce18697f719473f', '1c0qjj3cna3lmuee5ocsjp1sb6ghmk6t65sl3qvqu1pjopotb51n');
insert into M_USER (ID,muname,mpwd, salt) values (5,'Schorsch','31205d955e9483a93461b57888fd3a8c2a417b53bbc2def17ce18697f719473f', '1c0qjj3cna3lmuee5ocsjp1sb6ghmk6t65sl3qvqu1pjopotb51n');
insert into M_USER (ID,muname,mpwd, salt) values (6,'Ignaz','31205d955e9483a93461b57888fd3a8c2a417b53bbc2def17ce18697f719473f', '1c0qjj3cna3lmuee5ocsjp1sb6ghmk6t65sl3qvqu1pjopotb51n');
