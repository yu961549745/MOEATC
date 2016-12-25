clc,clear,close all;
proxy=MoeaProxy();
algorithmName='NSGA-II';
problemName='Belegundu';
populationSize=100;
maxGenerations=2000;
nCalSize=200;
nCheckSize=5;
nPrecision=2;
tc=runMoea(proxy,algorithmName,problemName,populationSize,...
    maxGenerations,nCalSize,nCheckSize,nPrecision);

d=tc.getDistances();
m=tc.getMeans();
v=tc.getStds();
gen=(1:length(d))'+1;
v(1)=NaN;

figure;
plot(gen,d,'-r',gen,m,'-g',gen,v,'-b');
legend dis mean std
axis tight

figure;
semilogy(gen,d,'-r',gen,m,'-g',gen,v,'-b');
legend dis mean std
axis tight

figure;
semilogx(gen,d,'-r',gen,m,'-g',gen,v,'-b');
legend dis mean std
axis tight

p=tc.getParetoFront();
figure;
plot(p(:,1),p(:,2),'.');
axis tight

