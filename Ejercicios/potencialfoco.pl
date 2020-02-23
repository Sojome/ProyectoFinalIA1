%Main
:- use_module(library(pce)).
:- use_module(library(pce_style_item)).
main:-
	new(Menu, dialog('Sistema experto para un test vocacional', size(500,500))),
	new(L, label(nombre, 'Bienvenidos a su test')),
	new(@texto, label(nombre, 'Segun la respuestas dadas tendra su resultado:')),
	new(@respl, label(nombre, '')),
	new(Salir, button('Salir', and(message(Menu,destroy), message(Menu, free)))),
	new(@boton, button('Realizar test', message(@prolog, botones))),
	send(Menu, append(L)), new(@btnlvlpotencial, button('Diagnotico?')),
	send(Menu,display,L,point(100,20)),
	send(Menu,display,@boton,point(130,150)),
	send(Menu,display,@texto,point(50,100)),
	send(Menu,display,Salir,point(20,400)),
	send(Menu,display,@respl,point(20,130)),
	send(Menu,open_centered).

%Base de hechos

lvlpotencial('La luz exterior es baja,
la temperatura es fria,
entonces el potencial del foco esta encendida.
'):-consecuencia_1,!.

lvlpotencial('La luz exterior es baja,
la temperatura esta ambiental,
entonces el potencial del foco esta medio encendida.
'):-consecuencia_2,!.

lvlpotencial('La luz exterior es baja,
la temperatura esta caliente,
entonces el potencial del foco esta medio encendida.
'):-consecuencia_3,!.

lvlpotencial('La luz exterior esta ambiental,
la temperatura es fria,
entonces el potencial del foco esta media encendida.
'):-consecuencia_4,!.

lvlpotencial('La luz exterior esta ambiental,
la temperatura esta ambiental,
entonces el potencial del foco esta medio encendida.
'):-consecuencia_5,!.

lvlpotencial('La luz exterior esta ambiental,
la temperatura esta caliente,
entonces el potencial del foco esta apagada.
'):-consecuencia_6,!.

lvlpotencial('La luz exterior es alta,
la temperatura es fria,
entonces el potencial del foco esta medio encendida.
'):-consecuencia_7,!.

lvlpotencial('La luz exterior es alta,
la temperatura esta ambiental,
entonces el potencial del foco esta medio encendida.
'):-consecuencia_8,!.

lvlpotencial('La luz exterior es alta,
la temperatura esta caliente,
entonces el potencial del foco esta apagada.
'):-consecuencia_9,!.

lvlpotencial('No se puede determinar el potencial del foco').

%Reglas
/*-------------------------------------------------------------------------------------------------------------*/
consecuencia_1:- preg_consecuencia_1,
pregunta('-La temperatura esta alrededor del 0-20?').

consecuencia_2:- preg_consecuencia_2,
pregunta('-La temperatura esta alrededor del 20-35?').

consecuencia_3:- preg_consecuencia_3,
pregunta('-La temperatura esta alrededor del 35-100?').

consecuencia_4:- preg_consecuencia_4,
pregunta('La temperatura esta alrededor del 0-20?').

consecuencia_5:- preg_consecuencia_5,
pregunta('La temperatura esta alrededor del 20-35?').

consecuencia_6:- preg_consecuencia_6,
pregunta('La temperatura esta alrededor del 35-100?').

consecuencia_7:- preg_consecuencia_7,
pregunta('+La temperatura esta alrededor del 0-20?').

consecuencia_8:- preg_consecuencia_8,
pregunta('+La temperatura esta alrededor del 20-35?').

consecuencia_9:- preg_consecuencia_9,
pregunta('+La temperatura esta alrededor del 35-100?').

%Pregunta Iniciales
preg_consecuencia_1:-pregunta('La luz exterior esta alrededor del 0-30?'),!.
preg_consecuencia_2:-pregunta('La luz exterior esta alrededor del 0-30?'),!.
preg_consecuencia_3:-pregunta('La luz exterior esta alrededor del 0-30?'),!.
preg_consecuencia_4:-pregunta('La luz exterior esta alrededor del 30-70?'),!.
preg_consecuencia_5:-pregunta('La luz exterior esta alrededor del 30-70?'),!.
preg_consecuencia_6:-pregunta('La luz exterior esta alrededor del 30-70?'),!.
preg_consecuencia_7:-pregunta('La luz exterior esta alrededor del 70-100?'),!.
preg_consecuencia_8:-pregunta('La luz exterior esta alrededor del 70-100?'),!.
preg_consecuencia_9:-pregunta('La luz exterior esta alrededor del 70-100?'),!.
:-dynamic si/1,no/1.

preguntar(Problema):-new(Di, dialog('Identificar Orientacion vocacional')),
	new(L2, label(texto,'Responde las siguientes preguntas')),
	new(La, label(prob,Problema)),

	new(B1,button(si,and(message(Di,return,si)))),
	new(B2,button(no,and(message(Di,return,no)))),

	send(Di,append(L2)),
	send(Di,append(La)),
	send(Di,append(B1)),
	send(Di,append(B2)),

	send(Di,default_button,si),
	send(Di,open_centered),
	get(Di,confirm,Answer),
	write(Answer),send(Di,destroy),


	((Answer==si)->assert(si(Problema)); assert(no(Problema)),fail).

pregunta(S):- (si(S)->true; (no(S)->fail;preguntar(S))).
limpiar:- retract(si(_)),fail.
limpiar:- retract(no(_)),fail.
limpiar.


botones :-lim,
	send(@boton,free),
	send(@btnlvlpotencial,free),
	lvlpotencial(Enter),
	send(@texto, selection('De acuerdo con sus respuesta.')),
	send(@respl, selection(Enter)),
	new(@boton, button('Iniciar su evaluacion', message(@prolog, botones))),
	send(Menu,display,@boton,point(40,50)),
	send(Menu,display,@btnlvlpotencial,point(20,50)),
	limpiar.

lim:- send(@respl, selection('')).

limpiar2:-
	send(@texto,free),
	send(@respl,free),
	%send(@btnlvlpotencial,free),
	send(@boton,free).