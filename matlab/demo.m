clc,clear,close all;
proxy=MoeaProxy();
algorithmName='NSGA-III';
problemName='LZ6';
populationSize=200;
maxGenerations=2000;
nCalSize=500;
nCheckSize=20;
nPrecision=2;
tc=runMoea(proxy,algorithmName,problemName,populationSize,...
    maxGenerations,nCalSize,nCheckSize,nPrecision);

d=tc.getDistances();
m=tc.getMeans();
s=tc.getStds();
gen=(1:length(d))'+1;
s(1)=NaN;

mb=tc.getmBuffer();
sb=tc.getsBuffer();

figure;
plot(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend dis mean std
axis tight
drawnow;

figure;
semilogy(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend dis mean std
axis tight
drawnow;

figure;
semilogx(gen,d,'-r',gen,m,'-g',gen,s,'-b');
legend dis mean std
axis tight
drawnow;

p=tc.getParetoFront();
figure;
if size(p,2)==2
    plot(p(:,1),p(:,2),'.');
elseif size(p,2)==3
    plot3(p(:,1),p(:,2),p(:,3),'.');
end
box on;
grid on;
axis tight;
drawnow;


