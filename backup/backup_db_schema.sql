--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-07-04 22:59:50

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 227 (class 1259 OID 16459)
-- Name: alimento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alimento (
    id_alimento integer NOT NULL,
    nome_alimento character varying(100) NOT NULL,
    calorias numeric(6,2),
    proteinas numeric(6,2),
    carboidratos numeric(6,2)
);


ALTER TABLE public.alimento OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16458)
-- Name: alimento_id_alimento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alimento_id_alimento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.alimento_id_alimento_seq OWNER TO postgres;

--
-- TOC entry 4905 (class 0 OID 0)
-- Dependencies: 226
-- Name: alimento_id_alimento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alimento_id_alimento_seq OWNED BY public.alimento.id_alimento;


--
-- TOC entry 229 (class 1259 OID 16466)
-- Name: alimento_refeicao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alimento_refeicao (
    id_alimento_refeicao integer NOT NULL,
    id_refeicao integer NOT NULL,
    id_alimento integer NOT NULL,
    quantidade numeric(6,2) NOT NULL
);


ALTER TABLE public.alimento_refeicao OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16465)
-- Name: alimento_refeicao_id_alimento_refeicao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alimento_refeicao_id_alimento_refeicao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.alimento_refeicao_id_alimento_refeicao_seq OWNER TO postgres;

--
-- TOC entry 4906 (class 0 OID 0)
-- Dependencies: 228
-- Name: alimento_refeicao_id_alimento_refeicao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alimento_refeicao_id_alimento_refeicao_seq OWNED BY public.alimento_refeicao.id_alimento_refeicao;


--
-- TOC entry 220 (class 1259 OID 16413)
-- Name: plano_alimentar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plano_alimentar (
    id_plano integer NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.plano_alimentar OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16412)
-- Name: plano_alimentar_id_plano_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.plano_alimentar_id_plano_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.plano_alimentar_id_plano_seq OWNER TO postgres;

--
-- TOC entry 4907 (class 0 OID 0)
-- Dependencies: 219
-- Name: plano_alimentar_id_plano_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.plano_alimentar_id_plano_seq OWNED BY public.plano_alimentar.id_plano;


--
-- TOC entry 225 (class 1259 OID 16442)
-- Name: plano_alimentar_refeicao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plano_alimentar_refeicao (
    id_refeicao_plano integer NOT NULL,
    id_plano integer NOT NULL,
    id_refeicao integer NOT NULL,
    horario_refeicao time without time zone NOT NULL
);


ALTER TABLE public.plano_alimentar_refeicao OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16441)
-- Name: plano_alimentar_refeicao_id_refeicao_plano_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.plano_alimentar_refeicao_id_refeicao_plano_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.plano_alimentar_refeicao_id_refeicao_plano_seq OWNER TO postgres;

--
-- TOC entry 4908 (class 0 OID 0)
-- Dependencies: 224
-- Name: plano_alimentar_refeicao_id_refeicao_plano_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.plano_alimentar_refeicao_id_refeicao_plano_seq OWNED BY public.plano_alimentar_refeicao.id_refeicao_plano;


--
-- TOC entry 221 (class 1259 OID 16419)
-- Name: plano_alimentar_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plano_alimentar_usuario (
    id_usuario integer NOT NULL,
    id_plano integer NOT NULL,
    data_inicio date NOT NULL,
    data_fim date
);


ALTER TABLE public.plano_alimentar_usuario OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16435)
-- Name: refeicao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refeicao (
    id_refeicao integer NOT NULL,
    nome_refeicao character varying(100) NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.refeicao OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16434)
-- Name: refeicao_id_refeicao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.refeicao_id_refeicao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.refeicao_id_refeicao_seq OWNER TO postgres;

--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 222
-- Name: refeicao_id_refeicao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.refeicao_id_refeicao_seq OWNED BY public.refeicao.id_refeicao;


--
-- TOC entry 218 (class 1259 OID 16389)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nome character varying(100) NOT NULL,
    email character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    data_nascimento date,
    sexo character(1) NOT NULL,
    tipo_usuario character varying(20) DEFAULT 'usuario'::character varying,
    CONSTRAINT usuario_sexo_check CHECK ((sexo = ANY (ARRAY['M'::bpchar, 'F'::bpchar]))),
    CONSTRAINT usuario_tipo_usuario_check CHECK (((tipo_usuario)::text = ANY ((ARRAY['admin'::character varying, 'paciente'::character varying, 'nutricionista'::character varying])::text[])))
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16388)
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_usuario_seq OWNER TO postgres;

--
-- TOC entry 4910 (class 0 OID 0)
-- Dependencies: 217
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- TOC entry 4729 (class 2604 OID 16462)
-- Name: alimento id_alimento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alimento ALTER COLUMN id_alimento SET DEFAULT nextval('public.alimento_id_alimento_seq'::regclass);


--
-- TOC entry 4730 (class 2604 OID 16469)
-- Name: alimento_refeicao id_alimento_refeicao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alimento_refeicao ALTER COLUMN id_alimento_refeicao SET DEFAULT nextval('public.alimento_refeicao_id_alimento_refeicao_seq'::regclass);


--
-- TOC entry 4726 (class 2604 OID 16416)
-- Name: plano_alimentar id_plano; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar ALTER COLUMN id_plano SET DEFAULT nextval('public.plano_alimentar_id_plano_seq'::regclass);


--
-- TOC entry 4728 (class 2604 OID 16445)
-- Name: plano_alimentar_refeicao id_refeicao_plano; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_refeicao ALTER COLUMN id_refeicao_plano SET DEFAULT nextval('public.plano_alimentar_refeicao_id_refeicao_plano_seq'::regclass);


--
-- TOC entry 4727 (class 2604 OID 16438)
-- Name: refeicao id_refeicao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refeicao ALTER COLUMN id_refeicao SET DEFAULT nextval('public.refeicao_id_refeicao_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 16392)
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- TOC entry 4746 (class 2606 OID 16464)
-- Name: alimento alimento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alimento
    ADD CONSTRAINT alimento_pkey PRIMARY KEY (id_alimento);


--
-- TOC entry 4748 (class 2606 OID 16471)
-- Name: alimento_refeicao alimento_refeicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alimento_refeicao
    ADD CONSTRAINT alimento_refeicao_pkey PRIMARY KEY (id_alimento_refeicao);


--
-- TOC entry 4738 (class 2606 OID 16418)
-- Name: plano_alimentar plano_alimentar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar
    ADD CONSTRAINT plano_alimentar_pkey PRIMARY KEY (id_plano);


--
-- TOC entry 4744 (class 2606 OID 16447)
-- Name: plano_alimentar_refeicao plano_alimentar_refeicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_refeicao
    ADD CONSTRAINT plano_alimentar_refeicao_pkey PRIMARY KEY (id_refeicao_plano);


--
-- TOC entry 4740 (class 2606 OID 16423)
-- Name: plano_alimentar_usuario plano_alimentar_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_usuario
    ADD CONSTRAINT plano_alimentar_usuario_pkey PRIMARY KEY (id_usuario, id_plano, data_inicio);


--
-- TOC entry 4742 (class 2606 OID 16440)
-- Name: refeicao refeicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refeicao
    ADD CONSTRAINT refeicao_pkey PRIMARY KEY (id_refeicao);


--
-- TOC entry 4734 (class 2606 OID 24581)
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- TOC entry 4736 (class 2606 OID 16397)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);


--
-- TOC entry 4753 (class 2606 OID 16477)
-- Name: alimento_refeicao fk_alimento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alimento_refeicao
    ADD CONSTRAINT fk_alimento FOREIGN KEY (id_alimento) REFERENCES public.alimento(id_alimento);


--
-- TOC entry 4749 (class 2606 OID 16429)
-- Name: plano_alimentar_usuario fk_plano; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_usuario
    ADD CONSTRAINT fk_plano FOREIGN KEY (id_plano) REFERENCES public.plano_alimentar(id_plano);


--
-- TOC entry 4751 (class 2606 OID 16448)
-- Name: plano_alimentar_refeicao fk_plano_refeicao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_refeicao
    ADD CONSTRAINT fk_plano_refeicao FOREIGN KEY (id_plano) REFERENCES public.plano_alimentar(id_plano);


--
-- TOC entry 4752 (class 2606 OID 16453)
-- Name: plano_alimentar_refeicao fk_refeicao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_refeicao
    ADD CONSTRAINT fk_refeicao FOREIGN KEY (id_refeicao) REFERENCES public.refeicao(id_refeicao);


--
-- TOC entry 4754 (class 2606 OID 16472)
-- Name: alimento_refeicao fk_refeicao_alimento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alimento_refeicao
    ADD CONSTRAINT fk_refeicao_alimento FOREIGN KEY (id_refeicao) REFERENCES public.refeicao(id_refeicao);


--
-- TOC entry 4750 (class 2606 OID 16424)
-- Name: plano_alimentar_usuario fk_usuario_plano; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plano_alimentar_usuario
    ADD CONSTRAINT fk_usuario_plano FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


-- Completed on 2025-07-04 22:59:50

--
-- PostgreSQL database dump complete
--

