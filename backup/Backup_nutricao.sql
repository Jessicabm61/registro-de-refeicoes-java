PGDMP  :                    }            nutricionista_db    17.4    17.4 8    ,           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            -           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            .           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            /           1262    16387    nutricionista_db    DATABASE     v   CREATE DATABASE nutricionista_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'pt-BR';
     DROP DATABASE nutricionista_db;
                     postgres    false            �            1259    16459    alimento    TABLE     �   CREATE TABLE public.alimento (
    id_alimento integer NOT NULL,
    nome_alimento character varying(100) NOT NULL,
    calorias numeric(6,2),
    proteinas numeric(6,2),
    carboidratos numeric(6,2)
);
    DROP TABLE public.alimento;
       public         heap r       postgres    false            �            1259    16458    alimento_id_alimento_seq    SEQUENCE     �   CREATE SEQUENCE public.alimento_id_alimento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.alimento_id_alimento_seq;
       public               postgres    false    227            0           0    0    alimento_id_alimento_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.alimento_id_alimento_seq OWNED BY public.alimento.id_alimento;
          public               postgres    false    226            �            1259    16466    alimento_refeicao    TABLE     �   CREATE TABLE public.alimento_refeicao (
    id_alimento_refeicao integer NOT NULL,
    id_refeicao integer NOT NULL,
    id_alimento integer NOT NULL,
    quantidade numeric(6,2) NOT NULL
);
 %   DROP TABLE public.alimento_refeicao;
       public         heap r       postgres    false            �            1259    16465 *   alimento_refeicao_id_alimento_refeicao_seq    SEQUENCE     �   CREATE SEQUENCE public.alimento_refeicao_id_alimento_refeicao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE public.alimento_refeicao_id_alimento_refeicao_seq;
       public               postgres    false    229            1           0    0 *   alimento_refeicao_id_alimento_refeicao_seq    SEQUENCE OWNED BY     y   ALTER SEQUENCE public.alimento_refeicao_id_alimento_refeicao_seq OWNED BY public.alimento_refeicao.id_alimento_refeicao;
          public               postgres    false    228            �            1259    16413    plano_alimentar    TABLE     v   CREATE TABLE public.plano_alimentar (
    id_plano integer NOT NULL,
    descricao character varying(255) NOT NULL
);
 #   DROP TABLE public.plano_alimentar;
       public         heap r       postgres    false            �            1259    16412    plano_alimentar_id_plano_seq    SEQUENCE     �   CREATE SEQUENCE public.plano_alimentar_id_plano_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.plano_alimentar_id_plano_seq;
       public               postgres    false    220            2           0    0    plano_alimentar_id_plano_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.plano_alimentar_id_plano_seq OWNED BY public.plano_alimentar.id_plano;
          public               postgres    false    219            �            1259    16442    plano_alimentar_refeicao    TABLE     �   CREATE TABLE public.plano_alimentar_refeicao (
    id_refeicao_plano integer NOT NULL,
    id_plano integer NOT NULL,
    id_refeicao integer NOT NULL,
    horario_refeicao time without time zone NOT NULL
);
 ,   DROP TABLE public.plano_alimentar_refeicao;
       public         heap r       postgres    false            �            1259    16441 .   plano_alimentar_refeicao_id_refeicao_plano_seq    SEQUENCE     �   CREATE SEQUENCE public.plano_alimentar_refeicao_id_refeicao_plano_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 E   DROP SEQUENCE public.plano_alimentar_refeicao_id_refeicao_plano_seq;
       public               postgres    false    225            3           0    0 .   plano_alimentar_refeicao_id_refeicao_plano_seq    SEQUENCE OWNED BY     �   ALTER SEQUENCE public.plano_alimentar_refeicao_id_refeicao_plano_seq OWNED BY public.plano_alimentar_refeicao.id_refeicao_plano;
          public               postgres    false    224            �            1259    16419    plano_alimentar_usuario    TABLE     �   CREATE TABLE public.plano_alimentar_usuario (
    id_usuario integer NOT NULL,
    id_plano integer NOT NULL,
    data_inicio date NOT NULL,
    data_fim date
);
 +   DROP TABLE public.plano_alimentar_usuario;
       public         heap r       postgres    false            �            1259    16435    refeicao    TABLE     �   CREATE TABLE public.refeicao (
    id_refeicao integer NOT NULL,
    nome_refeicao character varying(100) NOT NULL,
    descricao character varying(255)
);
    DROP TABLE public.refeicao;
       public         heap r       postgres    false            �            1259    16434    refeicao_id_refeicao_seq    SEQUENCE     �   CREATE SEQUENCE public.refeicao_id_refeicao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.refeicao_id_refeicao_seq;
       public               postgres    false    223            4           0    0    refeicao_id_refeicao_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.refeicao_id_refeicao_seq OWNED BY public.refeicao.id_refeicao;
          public               postgres    false    222            �            1259    16389    usuario    TABLE     c  CREATE TABLE public.usuario (
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
    DROP TABLE public.usuario;
       public         heap r       postgres    false            �            1259    16388    usuario_id_usuario_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.usuario_id_usuario_seq;
       public               postgres    false    218            5           0    0    usuario_id_usuario_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;
          public               postgres    false    217            r           2604    16462    alimento id_alimento    DEFAULT     |   ALTER TABLE ONLY public.alimento ALTER COLUMN id_alimento SET DEFAULT nextval('public.alimento_id_alimento_seq'::regclass);
 C   ALTER TABLE public.alimento ALTER COLUMN id_alimento DROP DEFAULT;
       public               postgres    false    227    226    227            s           2604    16469 &   alimento_refeicao id_alimento_refeicao    DEFAULT     �   ALTER TABLE ONLY public.alimento_refeicao ALTER COLUMN id_alimento_refeicao SET DEFAULT nextval('public.alimento_refeicao_id_alimento_refeicao_seq'::regclass);
 U   ALTER TABLE public.alimento_refeicao ALTER COLUMN id_alimento_refeicao DROP DEFAULT;
       public               postgres    false    229    228    229            o           2604    16416    plano_alimentar id_plano    DEFAULT     �   ALTER TABLE ONLY public.plano_alimentar ALTER COLUMN id_plano SET DEFAULT nextval('public.plano_alimentar_id_plano_seq'::regclass);
 G   ALTER TABLE public.plano_alimentar ALTER COLUMN id_plano DROP DEFAULT;
       public               postgres    false    220    219    220            q           2604    16445 *   plano_alimentar_refeicao id_refeicao_plano    DEFAULT     �   ALTER TABLE ONLY public.plano_alimentar_refeicao ALTER COLUMN id_refeicao_plano SET DEFAULT nextval('public.plano_alimentar_refeicao_id_refeicao_plano_seq'::regclass);
 Y   ALTER TABLE public.plano_alimentar_refeicao ALTER COLUMN id_refeicao_plano DROP DEFAULT;
       public               postgres    false    225    224    225            p           2604    16438    refeicao id_refeicao    DEFAULT     |   ALTER TABLE ONLY public.refeicao ALTER COLUMN id_refeicao SET DEFAULT nextval('public.refeicao_id_refeicao_seq'::regclass);
 C   ALTER TABLE public.refeicao ALTER COLUMN id_refeicao DROP DEFAULT;
       public               postgres    false    223    222    223            m           2604    16392    usuario id_usuario    DEFAULT     x   ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);
 A   ALTER TABLE public.usuario ALTER COLUMN id_usuario DROP DEFAULT;
       public               postgres    false    218    217    218            '          0    16459    alimento 
   TABLE DATA           a   COPY public.alimento (id_alimento, nome_alimento, calorias, proteinas, carboidratos) FROM stdin;
    public               postgres    false    227   �H       )          0    16466    alimento_refeicao 
   TABLE DATA           g   COPY public.alimento_refeicao (id_alimento_refeicao, id_refeicao, id_alimento, quantidade) FROM stdin;
    public               postgres    false    229   O                  0    16413    plano_alimentar 
   TABLE DATA           >   COPY public.plano_alimentar (id_plano, descricao) FROM stdin;
    public               postgres    false    220   eO       %          0    16442    plano_alimentar_refeicao 
   TABLE DATA           n   COPY public.plano_alimentar_refeicao (id_refeicao_plano, id_plano, id_refeicao, horario_refeicao) FROM stdin;
    public               postgres    false    225   �O       !          0    16419    plano_alimentar_usuario 
   TABLE DATA           ^   COPY public.plano_alimentar_usuario (id_usuario, id_plano, data_inicio, data_fim) FROM stdin;
    public               postgres    false    221   �O       #          0    16435    refeicao 
   TABLE DATA           I   COPY public.refeicao (id_refeicao, nome_refeicao, descricao) FROM stdin;
    public               postgres    false    223   �O                 0    16389    usuario 
   TABLE DATA           f   COPY public.usuario (id_usuario, nome, email, senha, data_nascimento, sexo, tipo_usuario) FROM stdin;
    public               postgres    false    218   :P       6           0    0    alimento_id_alimento_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.alimento_id_alimento_seq', 115, true);
          public               postgres    false    226            7           0    0 *   alimento_refeicao_id_alimento_refeicao_seq    SEQUENCE SET     Y   SELECT pg_catalog.setval('public.alimento_refeicao_id_alimento_refeicao_seq', 10, true);
          public               postgres    false    228            8           0    0    plano_alimentar_id_plano_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.plano_alimentar_id_plano_seq', 5, true);
          public               postgres    false    219            9           0    0 .   plano_alimentar_refeicao_id_refeicao_plano_seq    SEQUENCE SET     \   SELECT pg_catalog.setval('public.plano_alimentar_refeicao_id_refeicao_plano_seq', 6, true);
          public               postgres    false    224            :           0    0    refeicao_id_refeicao_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.refeicao_id_refeicao_seq', 6, true);
          public               postgres    false    222            ;           0    0    usuario_id_usuario_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 13, true);
          public               postgres    false    217            �           2606    16464    alimento alimento_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.alimento
    ADD CONSTRAINT alimento_pkey PRIMARY KEY (id_alimento);
 @   ALTER TABLE ONLY public.alimento DROP CONSTRAINT alimento_pkey;
       public                 postgres    false    227            �           2606    16471 (   alimento_refeicao alimento_refeicao_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.alimento_refeicao
    ADD CONSTRAINT alimento_refeicao_pkey PRIMARY KEY (id_alimento_refeicao);
 R   ALTER TABLE ONLY public.alimento_refeicao DROP CONSTRAINT alimento_refeicao_pkey;
       public                 postgres    false    229            {           2606    16418 $   plano_alimentar plano_alimentar_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.plano_alimentar
    ADD CONSTRAINT plano_alimentar_pkey PRIMARY KEY (id_plano);
 N   ALTER TABLE ONLY public.plano_alimentar DROP CONSTRAINT plano_alimentar_pkey;
       public                 postgres    false    220            �           2606    16447 6   plano_alimentar_refeicao plano_alimentar_refeicao_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.plano_alimentar_refeicao
    ADD CONSTRAINT plano_alimentar_refeicao_pkey PRIMARY KEY (id_refeicao_plano);
 `   ALTER TABLE ONLY public.plano_alimentar_refeicao DROP CONSTRAINT plano_alimentar_refeicao_pkey;
       public                 postgres    false    225            }           2606    16423 4   plano_alimentar_usuario plano_alimentar_usuario_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.plano_alimentar_usuario
    ADD CONSTRAINT plano_alimentar_usuario_pkey PRIMARY KEY (id_usuario, id_plano, data_inicio);
 ^   ALTER TABLE ONLY public.plano_alimentar_usuario DROP CONSTRAINT plano_alimentar_usuario_pkey;
       public                 postgres    false    221    221    221                       2606    16440    refeicao refeicao_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.refeicao
    ADD CONSTRAINT refeicao_pkey PRIMARY KEY (id_refeicao);
 @   ALTER TABLE ONLY public.refeicao DROP CONSTRAINT refeicao_pkey;
       public                 postgres    false    223            w           2606    24581    usuario usuario_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);
 C   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_email_key;
       public                 postgres    false    218            y           2606    16397    usuario usuario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public                 postgres    false    218            �           2606    16477    alimento_refeicao fk_alimento    FK CONSTRAINT     �   ALTER TABLE ONLY public.alimento_refeicao
    ADD CONSTRAINT fk_alimento FOREIGN KEY (id_alimento) REFERENCES public.alimento(id_alimento);
 G   ALTER TABLE ONLY public.alimento_refeicao DROP CONSTRAINT fk_alimento;
       public               postgres    false    229    227    4739            �           2606    16429     plano_alimentar_usuario fk_plano    FK CONSTRAINT     �   ALTER TABLE ONLY public.plano_alimentar_usuario
    ADD CONSTRAINT fk_plano FOREIGN KEY (id_plano) REFERENCES public.plano_alimentar(id_plano);
 J   ALTER TABLE ONLY public.plano_alimentar_usuario DROP CONSTRAINT fk_plano;
       public               postgres    false    221    4731    220            �           2606    16448 *   plano_alimentar_refeicao fk_plano_refeicao    FK CONSTRAINT     �   ALTER TABLE ONLY public.plano_alimentar_refeicao
    ADD CONSTRAINT fk_plano_refeicao FOREIGN KEY (id_plano) REFERENCES public.plano_alimentar(id_plano);
 T   ALTER TABLE ONLY public.plano_alimentar_refeicao DROP CONSTRAINT fk_plano_refeicao;
       public               postgres    false    220    4731    225            �           2606    16453 $   plano_alimentar_refeicao fk_refeicao    FK CONSTRAINT     �   ALTER TABLE ONLY public.plano_alimentar_refeicao
    ADD CONSTRAINT fk_refeicao FOREIGN KEY (id_refeicao) REFERENCES public.refeicao(id_refeicao);
 N   ALTER TABLE ONLY public.plano_alimentar_refeicao DROP CONSTRAINT fk_refeicao;
       public               postgres    false    223    225    4735            �           2606    16472 &   alimento_refeicao fk_refeicao_alimento    FK CONSTRAINT     �   ALTER TABLE ONLY public.alimento_refeicao
    ADD CONSTRAINT fk_refeicao_alimento FOREIGN KEY (id_refeicao) REFERENCES public.refeicao(id_refeicao);
 P   ALTER TABLE ONLY public.alimento_refeicao DROP CONSTRAINT fk_refeicao_alimento;
       public               postgres    false    4735    229    223            �           2606    16424 (   plano_alimentar_usuario fk_usuario_plano    FK CONSTRAINT     �   ALTER TABLE ONLY public.plano_alimentar_usuario
    ADD CONSTRAINT fk_usuario_plano FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 R   ALTER TABLE ONLY public.plano_alimentar_usuario DROP CONSTRAINT fk_usuario_plano;
       public               postgres    false    218    4729    221            '     x��WKnG]W���`�߳���0�)Fd3"[��2�(�w>CN�xa$[�����33�c%@���W�^59-��{C\�EY�(LE��2Ag��=��l�0��(4g������dt0I^��B��E}�p|OZKY����L�I��\,8R�pN1C'����6{�*����(fq�?�6p�����Ӻo�⤻oU�T�I�"��^�wd\�BZ�[m/���u�ʘ��)����Fe�5)��T�8�)�]v�����dr�PXŸ��H`D�
52��opDśd!���MϺ����e}�zK�J.ʒ�Q���wO��%	1�--��U���ŋ�d���CHR<� �*4��2�O��ʓ3�n���L�t��u����`sQT��+$��m��y��)�R��� �^�mݮ��F����L�)]b�̥�P�qLh�����r�p�N����p�p��LX����MG�3p�]�[C�����
c���y��Ab&3�!�)t�d	f]����J����J�r�k�xt��?n"�zյ�S�d�P�I��i7Cs�P�'��s��tQo��q�n� ����i��A�idL�Z.�i��>����
5g�hyv�4n�T����f{|w�x�>�G�t�__�et�Im'�@�-:�k�S%�36��}̟��z�`�T�o��E��']��!)6V	j��bJ�Y�7�M���@B�0.��)$g���pu|�1�*���x�#��1S.� D���|4[!n5�x��u{8��� IsC#gn<��=�	��h�Y�/����1j�1fRx��k��̗$9z�������<Fi>�~��9��nl�:h��7���:�n����~��I��e+�b�R���ڷ�zd��%����nq~���3�1]��YY��}M����*Do #[��b�qN*�PA��2u��z���@�r�@��lZ�|��ic3^�*�%3�Z�HvZ��"�O$B������G��?=�;3���~�ej��cT)����o��,ILXǏWP�O: m3���u��So3,�3qY���ph�s̖0|��W�j�Aɫ�Q-���)�W ��I�"�TVA�7��o���d8&�`Vӷ�;��u�����F2k�o7�ly�6���̆%��a������t����������)4��p��&X�
�&4;j1z���x������$����Zno�a|@�H�E�-�w�����(D�\��=����0薵�͉B� ���	J4n�Y�:�(�T��]��ە���r�g���[�a���L��
+g�W�{�9���x�����c��IZB�y望5J$Wl������4oj�`��rwҺ�iCM����_$b��s�?@3��%U��P�QG��~���M*�)�A�L���{���L����U�������9�Xwu���[<��Y'ǮLz+����]�����4\��"|��%g����
�G�����@�dQ��K���7�Fr^J�e\�V�ӓ{�,�p��S�U��'G>�w�5^�GW�D�����-^�~�G+��L����VNǷ���`��t����C`+^����'(����1c���ˮ�����\J���qz��{���`�`��D������K����/
      )   F   x�5��� �����K��#�#n�Z�H�T��TN6�f��vV�&�y/O)��a�v7�b��!"�H�             x�3�t.-)��K�2�L*���b���� cy      %   #   x�3�4�4�4��20 ".3 ה��ʍ���� bXb      !      x�34�4�4202�5 !�?�=... 3Q�      #   7   x�3�tNLK�L\F��9����P�˔�+1�$�3Dq�q:�f&�	�=... "m-         �   x���1�0�������$:8���1G�gJ��*����w�{�:7�l	Cj{�����mJ覩2�3S�������I�g�42��?��En.DAHݳE���okp�
��������n����id���Vh�j?Su͕ROF2[�     