--
-- PostgreSQL database dump
--

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.0

-- Started on 2025-09-07 13:45:06

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
-- TOC entry 222 (class 1259 OID 16533)
-- Name: permisos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permisos (
    id integer NOT NULL,
    codigo character varying(50) NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    recurso character varying(50),
    accion character varying(50),
    activo boolean DEFAULT true NOT NULL,
    fecha_creacion timestamp without time zone NOT NULL,
    fecha_actualizacion timestamp without time zone NOT NULL
);


ALTER TABLE public.permisos OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16543)
-- Name: rol_permisos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rol_permisos (
    rol_id integer NOT NULL,
    permiso_id integer NOT NULL
);


ALTER TABLE public.rol_permisos OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16523)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    codigo character varying(20) NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    activo boolean DEFAULT true NOT NULL,
    fecha_creacion timestamp without time zone NOT NULL,
    fecha_actualizacion timestamp without time zone NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16458)
-- Name: solicitud; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solicitud (
    id integer NOT NULL,
    documento_identidad_cliente character varying(20) NOT NULL,
    tipo_prestamo character varying(50) NOT NULL,
    monto numeric(15,2) NOT NULL,
    plazo_meses integer NOT NULL,
    estado character varying(30) NOT NULL,
    observaciones text,
    create_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.solicitud OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16457)
-- Name: solicitud_idsolicitud_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.solicitud ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.solicitud_idsolicitud_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16445)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    idusuario integer NOT NULL,
    nombres character varying(50) NOT NULL,
    apellidos character varying(50) NOT NULL,
    telefono character varying(15),
    direccion character varying(200) NOT NULL,
    fecha_nacimiento date NOT NULL,
    correo_electronico character varying(100) NOT NULL,
    salario_base numeric(10,2) NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    create_at timestamp without time zone DEFAULT now() NOT NULL,
    update_at timestamp without time zone DEFAULT now() NOT NULL,
    password character varying,
    documento_identidad character varying,
    rol character varying,
    estado character varying(20)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16444)
-- Name: usuario_idusuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.usuario ALTER COLUMN idusuario ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.usuario_idusuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 4887 (class 0 OID 16533)
-- Dependencies: 222
-- Data for Name: permisos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.permisos (id, codigo, nombre, descripcion, recurso, accion, activo, fecha_creacion, fecha_actualizacion) FROM stdin;
1	CREAR_USUARIO	Crear Usuario	Permite crear nuevos usuarios	USUARIO	CREAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
2	CONSULTAR_USUARIO	Consultar Usuario	Permite consultar información de usuarios	USUARIO	CONSULTAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
3	ACTUALIZAR_USUARIO	Actualizar Usuario	Permite actualizar información de usuarios	USUARIO	ACTUALIZAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
4	ELIMINAR_USUARIO	Eliminar Usuario	Permite eliminar usuarios	USUARIO	ELIMINAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
5	CREAR_SOLICITUD	Crear Solicitud	Permite crear solicitudes de préstamo	SOLICITUD	CREAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
6	CONSULTAR_SOLICITUD	Consultar Solicitud	Permite consultar solicitudes	SOLICITUD	CONSULTAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
7	ACTUALIZAR_SOLICITUD	Actualizar Solicitud	Permite actualizar solicitudes	SOLICITUD	ACTUALIZAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
8	APROBAR_SOLICITUD	Aprobar Solicitud	Permite aprobar solicitudes de préstamo	SOLICITUD	APROBAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
9	RECHAZAR_SOLICITUD	Rechazar Solicitud	Permite rechazar solicitudes	SOLICITUD	RECHAZAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
10	DESEMBOLSAR_SOLICITUD	Desembolsar Solicitud	Permite desembolsar préstamos	SOLICITUD	DESEMBOLSAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
11	GENERAR_REPORTES	Generar Reportes	Permite generar reportes del sistema	REPORTE	GENERAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
12	CONSULTAR_ESTADISTICAS	Consultar Estadísticas	Permite consultar estadísticas	ESTADISTICA	CONSULTAR	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
\.


--
-- TOC entry 4888 (class 0 OID 16543)
-- Dependencies: 223
-- Data for Name: rol_permisos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rol_permisos (rol_id, permiso_id) FROM stdin;
1	1
1	2
1	3
1	4
1	5
1	6
1	7
1	8
1	9
1	10
1	11
1	12
2	1
2	2
2	5
2	6
2	8
2	9
2	11
2	12
3	5
3	6
\.


--
-- TOC entry 4886 (class 0 OID 16523)
-- Dependencies: 221
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, codigo, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) FROM stdin;
1	ADMIN	Administrador	Acceso completo al sistema	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
2	ASESOR	Asesor	Gestión de usuarios y solicitudes	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
3	CLIENTE	Cliente	Creación y consulta de solicitudes propias	t	2025-09-03 21:22:24.461189	2025-09-03 21:22:24.461189
\.


--
-- TOC entry 4885 (class 0 OID 16458)
-- Dependencies: 220
-- Data for Name: solicitud; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solicitud (id, documento_identidad_cliente, tipo_prestamo, monto, plazo_meses, estado, observaciones, create_at, update_at) FROM stdin;
2	71234567	Préstamo Personal	1500.00	12	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 16:23:34.958723	2025-08-28 16:23:34.958723
3	71234567	Préstamo Personal	1500.00	12	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 17:09:16.412935	2025-08-28 17:09:16.412935
4	71234567	Préstamo Personal	1500.00	12	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 18:59:14.631584	2025-08-28 18:59:14.631584
5	71234567	Préstamo Personal	1500.00	12	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 19:01:53.171719	2025-08-28 19:01:53.171719
6	71234567	Préstamo Personal	1500.00	12	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 19:05:23.854176	2025-08-28 19:05:23.854176
7	71234567	Préstamo Personal	100.00	12	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 19:14:06.985823	2025-08-28 19:14:06.985823
8	71234567	Préstamo Personal	100.00	2	Pendiente de revisión	Ingreso mensual: 3500; sin deudas vigentes	2025-08-28 19:16:01.567239	2025-08-28 19:16:01.567239
13	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 03:44:49.483441	2025-09-03 03:44:49.483441
14	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 03:48:47.721276	2025-09-03 03:48:47.721276
15	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 07:52:03.556011	2025-09-03 07:52:03.556011
16	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 07:55:32.692677	2025-09-03 07:55:32.692677
17	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 07:56:36.434773	2025-09-03 07:56:36.434773
18	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 07:57:15.458776	2025-09-03 07:57:15.458776
19	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:11:40.545913	2025-09-03 08:11:40.545913
20	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:14:20.609643	2025-09-03 08:14:20.609643
21	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:20:26.210396	2025-09-03 08:20:26.210396
22	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:21:04.115363	2025-09-03 08:21:04.115363
23	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:27:21.059734	2025-09-03 08:27:21.817041
24	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:39:56.79709	2025-09-03 08:39:57.521186
25	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-03 08:53:23.091289	2025-09-03 08:53:23.968911
26	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-05 01:09:09.796553	2025-09-05 01:09:10.363065
27	71234567	Crédito de consumo	501.00	12	PENDIENTE_REVISION	\N	2025-09-05 16:14:50.933948	2025-09-05 16:14:51.344435
\.


--
-- TOC entry 4883 (class 0 OID 16445)
-- Dependencies: 218
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (idusuario, nombres, apellidos, telefono, direccion, fecha_nacimiento, correo_electronico, salario_base, activo, create_at, update_at, password, documento_identidad, rol, estado) FROM stdin;
23	Juna tony	Ramírez López	\N	Av. Los Álamos 123, San Miguel	1990-05-15	juan.ramirez@example.com	15000000.00	t	2025-09-07 12:16:54.6377	2025-09-07 12:16:59.248947	$2a$10$ogzQtHp3LI.6uglQoz.63uTwI15fIN9SqBF9jsJWD9cqI4pLzyer6	71380850	ASESOR	ACTIVO
24	Max Javier	Abad Santur	\N	Av. Los Álamos 123, San Miguel	1990-05-15	juan.ramirez1@example.com	15000000.00	t	2025-09-07 12:17:54.985236	2025-09-07 12:17:55.050619	$2a$10$M.TqdPT5J7ey80/gGTgoj..pMh3m9EgHLRXN0s2mTqz.eCABHrGZu	71380848	CLIENTE	ACTIVO
\.


--
-- TOC entry 4894 (class 0 OID 0)
-- Dependencies: 219
-- Name: solicitud_idsolicitud_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solicitud_idsolicitud_seq', 27, true);


--
-- TOC entry 4895 (class 0 OID 0)
-- Dependencies: 217
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_idusuario_seq', 24, true);


--
-- TOC entry 4730 (class 2606 OID 16542)
-- Name: permisos permisos_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permisos
    ADD CONSTRAINT permisos_codigo_key UNIQUE (codigo);


--
-- TOC entry 4732 (class 2606 OID 16540)
-- Name: permisos permisos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permisos
    ADD CONSTRAINT permisos_pkey PRIMARY KEY (id);


--
-- TOC entry 4734 (class 2606 OID 16547)
-- Name: rol_permisos rol_permisos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_permisos
    ADD CONSTRAINT rol_permisos_pkey PRIMARY KEY (rol_id, permiso_id);


--
-- TOC entry 4726 (class 2606 OID 16532)
-- Name: roles roles_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_codigo_key UNIQUE (codigo);


--
-- TOC entry 4728 (class 2606 OID 16530)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4724 (class 2606 OID 16466)
-- Name: solicitud solicitud_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitud
    ADD CONSTRAINT solicitud_pkey PRIMARY KEY (id);


--
-- TOC entry 4720 (class 2606 OID 16454)
-- Name: usuario usuario_correo_electronico_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_correo_electronico_key UNIQUE (correo_electronico);


--
-- TOC entry 4722 (class 2606 OID 16452)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- TOC entry 4735 (class 2606 OID 16553)
-- Name: rol_permisos rol_permisos_permiso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_permisos
    ADD CONSTRAINT rol_permisos_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES public.permisos(id) ON DELETE CASCADE;


--
-- TOC entry 4736 (class 2606 OID 16548)
-- Name: rol_permisos rol_permisos_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_permisos
    ADD CONSTRAINT rol_permisos_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES public.roles(id) ON DELETE CASCADE;


-- Completed on 2025-09-07 13:45:06

--
-- PostgreSQL database dump complete
--

