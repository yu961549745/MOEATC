clc,clear,close all;
javaaddpath('./MOEATC.jar');
import moeatc.*

A=rand(1000);

tic
h=HungarianAlgorithm(A);
J=h.execute()+1;
toc

tic
V=lapjv(A);
toc

isequal(J(:),V(:))