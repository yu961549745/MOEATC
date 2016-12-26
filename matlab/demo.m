clc,clear,close all;
disp('����MOEA����')
tic
% ��Ϊ�ǵ���ģʽʹ����persistent������
% �����滻jar�ļ�ʱ����Ҫ clear all ������Ч
proxy=MoeaProxy.getInstance();
toc
algorithmName='NSGA-III';
problemName='Binh2';
populationSize=100;
maxGenerations=2000;
nCalSize=100;
nCheckSize=10;
nPrecision=2;
recordGens=[];
disp('ִ��MOEA����')
tic
[v,p,d,m,s,~,pfs]=runMoea(proxy,algorithmName,problemName,populationSize,...
    maxGenerations,nCalSize,nCheckSize,nPrecision,recordGens);
toc

gen=(1:length(d))'+1;
s(1)=NaN;

figure;
plot(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend Dt Mt St
axis tight
xlabel('Generations')
ylabel('Dt Mt St')
drawnow;

% figure;
% semilogy(gen,d,'-r',gen,m,'-g',gen,s,'-b');
% legend Dt Mt St
% axis tight
% xlabel('Generations')
% ylabel('Dt Mt St (log scale)')
% drawnow;
% 
% figure;
% semilogx(gen,d,'-r',gen,m,'-g',gen,s,'-b');
% legend Dt Mt St
% axis tight
% xlabel('Generations (log scale)')
% ylabel('Dt Mt St')
% drawnow;

figure;
hold on;
plot(p(:,1),p(:,2),'.r');
box on;
axis tight;
title('Pareto Front');
drawnow;


