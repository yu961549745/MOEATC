clc,clear,close all;
disp('加载MOEA服务')
tic
proxy=MoeaProxy.getInstance();
toc
algorithmName='NSGA-III';
problemName='Belegundu';
populationSize=100;
maxGenerations=1000;
nCalSize=100;
nCheckSize=10;
nPrecision=2;
recordGens=[];
disp('执行MOEA计算')
tic
[v,p,d,m,s,pfs]=runMoea(proxy,algorithmName,problemName,populationSize,...
    maxGenerations,nCalSize,nCheckSize,nPrecision,recordGens);
toc

gen=(1:length(d))';
s(1)=NaN;

figure;
plot(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend Dt Mt St
axis tight
xlabel('Generations')
ylabel('Dt Mt St')
drawnow;

figure;
hold on;
plot(p(:,1),p(:,2),'.r');
box on;
axis tight;
title('Pareto Front');
drawnow;


