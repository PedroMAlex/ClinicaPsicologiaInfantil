/* POPULANDO DADOS DA TABELA DE ESPECILIDADES */
insert into especialidade (descricao) values ('PSICOLOGIA');
insert into especialidade (descricao) values ('PSIQUIATRIA');
insert into especialidade (descricao) values ('PSICOMOTRICIDADE');
insert into especialidade (descricao) values ('TERAPIA OCUPACIONAL');

/* POPULANDO DADOS DA TABELA DE MEDICOS */
insert into medico (id_especialidade, nome, crm, cpf, email, contato) values (1, 'HEBERT RICHERS', '432984/RJ', '53254859030', 'hebertrichers@psicologiainfantil.com.br', '(21)99876-1234');
insert into medico (id_especialidade, nome, crm, cpf, email, contato) values (2, 'ANTONIO BANDERAS', '432984/RN', '67154904062', 'antoniobanderas@psicologiainfantil.com.br', '(84)97652-8230');
insert into medico (id_especialidade, nome, crm, cpf, email, contato) values (3, 'RONALD REAGAN', '432984/RS', '51983715050', 'ronaldreagan@psicologiainfantil.com.br', '(51)92307-1266');
insert into medico (id_especialidade, nome, crm, cpf, email, contato) values (4, 'STEVEN SPIELBERG', '432984/RR', '55414320063', 'stevenspielberg@psicologiainfantil.com.br', '(95)93245-8712');

/* POPULANDO DADOS DA TABELA DE PACIENTES */
insert into paciente (nome, cpf, email, contato) values ('ARNOLD SCHWARZENEGGER', '55946321072', 'schwarzenegger@gmail.com.br', '(84)98746-2477');
insert into paciente (nome, cpf, email, contato) values ('SYLVESTER STALLONE', '72479729044', 'stallone@gmail.com.br', '(84)97146-1275');
insert into paciente (nome, cpf, email, contato) values ('JASON STATHAM', '78743059066', 'j.statham@gmail.com.br', '(84)96322-0000');
insert into paciente (nome, cpf, email, contato) values ('DOLPH LUNDGREN', '55889494040', 'dolphlundgren@gmail.com.br', '(84)95432-1278');
insert into paciente (nome, cpf, email, contato) values ('JEAN-CLAUDE', '97200195065', 'jcvd@gmail.com.br', '(84)97546-0027');

/* POPULANDO DADOS NA TABELA DE RECEITAS */
insert into receita (id_paciente, id_medico, data, hora, prescricao) values (1, 1, '2023-03-12', PARSEDATETIME('16:22', 'HH:mm'), 'DIPIRONA 500MG, TOMAR 2X AO DIA');
insert into receita (id_paciente, id_medico, data, hora, prescricao) values (1, 2, '2023-05-10', PARSEDATETIME('09:00', 'HH:mm'), 'LUFTAL 125MG, TOMAR 30 MINUTOS APÓS AS REFEIÇÕES');
insert into receita (id_paciente, id_medico, data, hora, prescricao) values (1, 1, '2023-07-06', PARSEDATETIME('10:30', 'HH:mm'), 'DRAMIN 25MG, TOMAR 2X AO DIA');
insert into receita (id_paciente, id_medico, data, hora, prescricao) values (2, 3, '2023-01-31', PARSEDATETIME('10:00', 'HH:mm'), 'DIPIRONA 500MG, TOMAR 2X AO DIA');
insert into receita (id_paciente, id_medico, data, hora, prescricao) values (2, 4, '2023-04-13', PARSEDATETIME('09:00', 'HH:mm'), 'LUFTAL 125MG, TOMAR 30 MINUTOS APÓS AS REFEIÇÕES');
insert into receita (id_paciente, id_medico, data, hora, prescricao) values (2, 2, '2023-09-23', PARSEDATETIME('10:30', 'HH:mm'), 'DRAMIN 25MG, TOMAR 2X AO DIA');

/* POPULANDO DADOS NA TABELA DE RECEITAS */
insert into consulta (id_paciente, id_medico, data, hora, devolutiva) values (1, 3, '2023-09-01', PARSEDATETIME('16:22', 'HH:mm'), 'ESPECTRO AUTISTA');
insert into consulta (id_paciente, id_medico, data, hora, devolutiva) values (1, 3, '2023-01-21', PARSEDATETIME('16:22', 'HH:mm'), 'TDH EM INVESTIGAÇÃO');
insert into consulta (id_paciente, id_medico, data, hora, devolutiva) values (2, 3, '2023-07-13', PARSEDATETIME('16:22', 'HH:mm'), 'ESPECTRO AUTISTA');
insert into consulta (id_paciente, id_medico, data, hora, devolutiva) values (2, 2, '2023-10-14', PARSEDATETIME('16:22', 'HH:mm'), 'TDH EM INVESTIGAÇÃO');
insert into consulta (id_paciente, id_medico, data, hora, devolutiva) values (3, 2, '2023-11-01', PARSEDATETIME('16:22', 'HH:mm'), 'ESPECTRO AUTISTA');