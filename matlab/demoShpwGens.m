clc,clear,close all;
fid=fopen('dis.txt');
X=textscan(fid,'%d %f %f %f');
fclose(fid);
gen=X{1};
d=X{2};
m=X{3};
v=X{4};
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
