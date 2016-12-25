clc,clear,close all;
proxy=MoeaProxy();
algorithmName='NSGA-III';
problemName='Belegundu';
populationSize=100;
maxGenerations=2000;
nCalSize=50;
nCheckSize=10;
nPrecision=2;
tc=runMoea(proxy,algorithmName,problemName,populationSize,...
    maxGenerations,nCalSize,nCheckSize,nPrecision);

d=tc.getDistances();
m=tc.getMeans();
s=tc.getStds();
gen=(1:length(d))'+1;
s(1)=NaN;

figure;
plot(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend Dt Mt St
axis tight
xlabel('Generations')
ylabel('Dt Mt St')
drawnow;

figure;
semilogy(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend dis mean std
axis tight
xlabel('Generations')
ylabel('Dt Mt St (log scale)')
drawnow;

figure;
semilogx(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend dis mean std
axis tight
xlabel('Generations (log scale)')
ylabel('Dt Mt St')
drawnow;

p=tc.getParetoFront();
figure;
if size(p,2)==2
    plot(p(:,1),p(:,2),'.');
elseif size(p,2)==3
    plot3(p(:,1),p(:,2),p(:,3),'.');
    grid on;
end
box on;
axis tight;
title('Pareto Front');
drawnow;


