% �Ƚϲ�ͬ�ľ�ֵ�ͱ�׼����㷽��
clc,clear,close all;
disp('����MOEA����')
tic
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
[~,~,d,m,s,pfs]=runMoea(proxy,algorithmName,problemName,populationSize,...
    maxGenerations,nCalSize,nCheckSize,nPrecision,recordGens);
toc

% ��ͼ
figure;
hold on;
mm=cummean(d);
ss=cumstd(d);
plot(d,'-r');
plot(m,'-g');
plot(s,'-b');
plot(mm,'--g');
plot(ss,'--b');
legend('Dt','Mt','St','Mt''','St''');
box on;
axis tight;
xlabel Generations
ylabel Dissimilarity
