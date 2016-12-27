function y = cumstd(x)
y = sqrt(cummean(x.^2)-cummean(x).^2);
end