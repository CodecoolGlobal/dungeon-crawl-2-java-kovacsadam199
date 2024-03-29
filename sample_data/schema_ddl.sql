DROP TABLE IF EXISTS public.game_state CASCADE;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    current_map text NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL
);

DROP TABLE IF EXISTS public.player CASCADE;
CREATE TABLE public.player (
    id serial NOT NULL PRIMARY KEY,
    player_name text NOT NULL,
    hp integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    inventory text
);

DROP TABLE IF EXISTS public.monsters CASCADE;
CREATE TABLE public.monsters (
   id serial NOT NULL PRIMARY KEY,
   tile_name text NOT NULL,
   hp integer NOT NULL,
   x integer NOT NULL,
   y integer NOT NULL,
   game_state_id integer NOT NULL
);

DROP TABLE IF EXISTS public.elements CASCADE;
CREATE TABLE public.elements (
     id serial NOT NULL PRIMARY KEY,
     tile_name text NOT NULL,
     is_changed boolean NOT NULL,
     x integer NOT NULL,
     y integer NOT NULL,
     game_state_id integer NOT NULL
);

DROP TABLE IF EXISTS public.items CASCADE;
CREATE TABLE public.items (
     id serial NOT NULL PRIMARY KEY,
     tile_name text NOT NULL,
     is_picked boolean NOT NULL,
     x integer NOT NULL,
     y integer NOT NULL,
     game_state_id integer NOT NULL
);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.monsters
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state(id) ON DELETE CASCADE;;

ALTER TABLE ONLY public.elements
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state(id) ON DELETE CASCADE;;

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state(id) ON DELETE CASCADE;;