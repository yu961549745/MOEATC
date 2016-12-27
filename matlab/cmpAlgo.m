% 比较不同的算法的影响
clc,clear,close all;
disp('加载MOEA服务')
tic
proxy=MoeaProxy.getInstance();
toc
algorithmNames={'NSGA-II','eMOEA','MOEAD','NSGA-III'};
problemNames={'Belegundu','Viennet2','CF2','CF10','LZ1','UF8'};
populationSize=100;
maxGenerations=1000;
nCalSize=100;
nCheckSize=10;
nPrecision=2;
TestTimes=10;

m=length(problemNames);
n=length(algorithmNames);
MD=zeros(m,n);SD=zeros(m,n);
MN=zeros(m,n);SN=zeros(m,n);
for i=1:m
    for j=1:n
        problemName=problemNames{i};
        algorithmName=algorithmNames{j};
        dpq=zeros(TestTimes,1);
        ngt=zeros(TestTimes,1);
        for k=1:TestTimes
            [~,~,~,mm]=runMoea(proxy,algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision);
            dpq(k)=mm(end);
            ngt(k)=length(mm);
        end
        MD(i,j)=mean(dpq);SD(i,j)=std(dpq,1);
        MN(i,j)=mean(ngt);SN(i,j)=std(ngt,1);
        fprintf('%s %s %.0f±%.0f %.4f±%.4f\n',problemName,algorithmName,...
            MN(i,j),SN(i,j),MD(i,j),SD(i,j));
    end
end

fid=fopen('algo.csv','w');
for i=1:m
    fprintf(fid,'%s,',problemNames{i});
    for j=1:n
        fprintf(fid,'%.0f±%.0f,',MN(i,j),SN(i,j));
    end
    fprintf(fid,',');
    for j=1:n
        fprintf(fid,'%.4f±%.4f,',MD(i,j),SD(i,j));
    end
    fprintf(fid,'\n');
end
fclose(fid);