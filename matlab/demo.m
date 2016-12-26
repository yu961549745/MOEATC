clc,clear,close all;
disp('加载MOEA服务')
tic
% 因为是单例模式使用了persistent变量，
% 所以替换jar文件时，需要 clear all 才能生效
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
disp('执行MOEA计算')
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


