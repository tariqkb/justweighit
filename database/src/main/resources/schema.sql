--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: food; Type: TABLE; Schema: public; Owner: jwi; Tablespace:
--

CREATE TABLE food (
                      ndbno character varying(128) NOT NULL,
                      description character varying(1024),
                      short_description character varying(256)
);


ALTER TABLE public.food OWNER TO jwi;

--
-- Name: nutrient; Type: TABLE; Schema: public; Owner: jwi; Tablespace:
--

CREATE TABLE nutrient (
                          id integer NOT NULL,
                          name character varying(128),
                          unit character varying(32)
);


ALTER TABLE public.nutrient OWNER TO jwi;

--
-- Name: nutrient_content; Type: TABLE; Schema: public; Owner: jwi; Tablespace:
--

CREATE TABLE nutrient_content (
                                  ndbno character varying(128) NOT NULL,
                                  nutrient_id integer NOT NULL,
                                  value numeric NOT NULL
);


ALTER TABLE public.nutrient_content OWNER TO jwi;

--
-- Name: weight; Type: TABLE; Schema: public; Owner: jwi; Tablespace:
--

CREATE TABLE weight (
                        ndbno character varying(128) NOT NULL,
                        amount numeric,
                        unit integer,
                        description character varying(256),
                        grams numeric NOT NULL
);


ALTER TABLE public.weight OWNER TO jwi;

--
-- Name: food_pkey; Type: CONSTRAINT; Schema: public; Owner: jwi; Tablespace:
--

ALTER TABLE ONLY food
    ADD CONSTRAINT food_pkey PRIMARY KEY (ndbno);


--
-- Name: nutrient_content_pk; Type: CONSTRAINT; Schema: public; Owner: jwi; Tablespace:
--

ALTER TABLE ONLY nutrient_content
    ADD CONSTRAINT nutrient_content_pk PRIMARY KEY (ndbno, nutrient_id);


--
-- Name: nutrient_pkey; Type: CONSTRAINT; Schema: public; Owner: jwi; Tablespace:
--

ALTER TABLE ONLY nutrient
    ADD CONSTRAINT nutrient_pkey PRIMARY KEY (id);


--
-- Name: food_weight_key; Type: FK CONSTRAINT; Schema: public; Owner: jwi
--

ALTER TABLE ONLY weight
    ADD CONSTRAINT food_weight_key FOREIGN KEY (ndbno) REFERENCES food(ndbno);


--
-- Name: nutrient_content_food_ndbno_fk; Type: FK CONSTRAINT; Schema: public; Owner: jwi
--

ALTER TABLE ONLY nutrient_content
    ADD CONSTRAINT nutrient_content_food_ndbno_fk FOREIGN KEY (ndbno) REFERENCES food(ndbno);


--
-- Name: nutrient_content_nutrient_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: jwi
--

ALTER TABLE ONLY nutrient_content
    ADD CONSTRAINT nutrient_content_nutrient_id_fk FOREIGN KEY (nutrient_id) REFERENCES nutrient(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

