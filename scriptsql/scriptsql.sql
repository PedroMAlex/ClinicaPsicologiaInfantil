     CREATE TABLE IF NOT EXISTS public.paciente (
            id SERIAL PRIMARY KEY,
            nome VARCHAR(100) NOT NULL,
            cpf VARCHAR(11),
            email VARCHAR(50),
            contato VARCHAR(15)
     ) TABLESPACE pg_clinica;

     CREATE TABLE IF NOT EXISTS public.medico (
            id SERIAL PRIMARY KEY,
            id_especialidade INTEGER NOT NULL,
            nome VARCHAR(100) NOT NULL,
            crm VARCHAR(30) NOT NULL,
            cpf VARCHAR(11),
            email VARCHAR(50),
            contato VARCHAR(15)
     ) TABLESPACE pg_clinica;

     CREATE TABLE IF NOT EXISTS public.consulta (
            id SERIAL PRIMARY KEY,
            id_paciente INTEGER NOT NULL,
            id_medico INTEGER NOT NULL,
            data DATE NOT NULL,
            hora TIME NOT NULL,
            devolutiva VARCHAR(255)
     ) TABLESPACE pg_clinica;

     CREATE TABLE IF NOT EXISTS public.receita (
            id SERIAL PRIMARY KEY,
            id_paciente INTEGER NOT NULL,
            id_medico INTEGER NOT NULL,
            descricao VARCHAR(255) NOT NULL
     ) TABLESPACE pg_clinica;

     CREATE TABLE IF NOT EXISTS public.especialidade (
            id SERIAL PRIMARY KEY,
            descricao VARCHAR(50) NOT NULL
     ) TABLESPACE pg_clinica;

     ALTER TABLE public.consulta
     ADD CONSTRAINT fk_consulta_paciente
     FOREIGN KEY (id_Paciente)
     REFERENCES public.paciente(id);

     ALTER TABLE public.consulta
     ADD CONSTRAINT fk_consulta_medico
     FOREIGN KEY (id_medico)
     REFERENCES public.medico(id);

     ALTER TABLE public.receita
     ADD CONSTRAINT fk_receita_paciente
     FOREIGN KEY (id_Paciente)
     REFERENCES public.paciente(id);

     ALTER TABLE public.receita
     ADD CONSTRAINT fk_receita_medico
     FOREIGN KEY (id_medico)
     REFERENCES public.medico(id);

     ALTER TABLE public.medico
     ADD CONSTRAINT fk_medico_especialidade
     FOREIGN KEY (id_especialidade)
     REFERENCES public.especialidade(id);