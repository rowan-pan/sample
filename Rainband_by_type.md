Rainband Analysis
================
Rowan Pan
10/19/2020

``` r
library(ggplot2)
rainbands_grouped <- read.csv('rainband_grouped.csv')
head(rainbands_grouped)
```

    ##   month day hour   year    width   latmin   latmax   lonmin   lonmax
    ## 1     1   1    0 1998.5 8.568182 28.15909 34.90909 129.3182 137.8864
    ## 2     1   1    6 1998.5 7.795455 29.11364 35.15909 128.8864 136.6818
    ## 3     1   1   12 1998.5 7.500000 27.55645 34.16129 127.4032 134.9032
    ## 4     1   1   18 1998.5 8.129032 27.70161 33.91935 125.1774 133.3065
    ## 5     1   2    0 1998.5 7.354839 27.38710 34.23387 126.1452 133.5000
    ## 6     1   2    6 1998.5 8.648438 29.13281 35.36719 125.2031 133.8516
    ##    rain.amount grid.size averagelat decimaldate
    ## 1 109961675679  56.81818   31.53409    1.031250
    ## 2 108380756064  55.96970   32.13636    1.039062
    ## 3 112142459113  55.61290   30.85887    1.046875
    ## 4 106550328202  59.83871   30.81048    1.054688
    ## 5 109168471043  55.90323   30.81048    1.062500
    ## 6 112664991040  61.34375   32.25000    1.070312

<img src="Rainband_by_type_files/figure-gfm/unnamed-chunk-2-1.png" style="display: block; margin: auto;" />

## El Niño

``` r
el_nino <- read.csv('el_nino_rainband.csv')
cat('Number of rows:', nrow(el_nino))
```

    ## Number of rows: 1464

``` r
head(el_nino)
```

    ##   month day hour     year    width   latmin   latmax   lonmin   lonmax
    ## 1     1   1    0 1996.889 11.34375 25.78125 34.12500 124.6875 136.0312
    ## 2     1   1    6 1996.889 11.06250 25.87500 33.84375 120.5625 131.6250
    ## 3     1   1   12 1996.889 10.08333 25.50000 31.58333 118.0833 128.1667
    ## 4     1   1   18 1996.889 10.58333 24.33333 30.58333 114.9167 125.5000
    ## 5     1   2    0 1996.889  9.25000 22.75000 30.25000 116.8333 126.0833
    ## 6     1   2    6 1996.889 15.00000 21.75000 30.09375 112.4062 127.4062
    ##    rain.amount grid.size averagelat decimaldate    type
    ## 1 115972009378  68.50000   29.95312    1.031250 El Nino
    ## 2 125324063308  78.00000   29.85938    1.039062 El Nino
    ## 3 116888816087  57.33333   28.54167    1.046875 El Nino
    ## 4 132816612491  70.55556   27.45833    1.054688 El Nino
    ## 5 144747219042  69.00000   26.50000    1.062500 El Nino
    ## 6 190903991119 103.12500   25.92188    1.070312 El Nino

<img src="Rainband_by_type_files/figure-gfm/unnamed-chunk-4-1.png" style="display: block; margin: auto;" />

## La Niña

``` r
la_nina <- read.csv('la_nina_rainband.csv')
cat('Number of rows:', nrow(la_nina))
```

    ## Number of rows: 1464

``` r
head(la_nina)
```

    ##   month day hour     year    width   latmin   latmax   lonmin   lonmax
    ## 1     1   1    0 2001.833 5.925000 31.35000 36.60000 133.2750 139.2000
    ## 2     1   1    6 2001.833 4.725000 31.50000 35.17500 134.6250 139.3500
    ## 3     1   1   12 2001.833 4.500000 30.32143 36.64286 130.1786 134.6786
    ## 4     1   1   18 2001.833 4.687500 31.40625 36.46875 131.2500 135.9375
    ## 5     1   2    0 2001.833 5.156250 31.78125 38.25000 129.0000 134.1562
    ## 6     1   2    6 2001.833 5.166667 32.83333 37.50000 125.7500 130.9167
    ##   rain.amount grid.size averagelat decimaldate    type
    ## 1 63032304140  34.80000   33.97500    1.031250 La Nina
    ## 2 38788506992  23.00000   33.33750    1.039062 La Nina
    ## 3 61201901619  37.00000   33.48214    1.046875 La Nina
    ## 4 58251167119  34.37500   33.93750    1.054688 La Nina
    ## 5 80072551834  43.62500   35.01562    1.062500 La Nina
    ## 6 70254559040  36.77778   35.16667    1.070312 La Nina

<img src="Rainband_by_type_files/figure-gfm/unnamed-chunk-6-1.png" style="display: block; margin: auto;" />

## Neutral

``` r
neutral <- read.csv('neutral_rainband.csv')
cat('Number of rows:', nrow(neutral))
```

    ## Number of rows: 1464

``` r
head(neutral)
```

    ##   month day hour     year    width   latmin   latmax   lonmin   lonmax
    ## 1     1   1    0 1997.158 8.850000 27.30000 34.20000 129.1500 138.0000
    ## 2     1   1    6 1997.158 8.100000 29.25000 35.85000 129.5000 137.6000
    ## 3     1   1   12 1997.158 7.350000 27.50000 34.55000 131.7000 139.0500
    ## 4     1   1   18 1997.158 8.517857 27.75000 34.60714 128.3036 136.8214
    ## 5     1   2    0 1997.158 7.392857 27.85714 34.50000 130.5000 137.8929
    ## 6     1   2    6 1997.158 7.350000 30.85000 36.90000 131.7000 139.0500
    ##    rain.amount grid.size averagelat decimaldate    type
    ## 1 138042412064  65.26667   30.75000    1.031250 Neutral
    ## 2 145739158249  66.20000   32.55000    1.039062 Neutral
    ## 3 133066905094  63.26667   31.02500    1.046875 Neutral
    ## 4 117264380349  67.50000   31.17857    1.054688 Neutral
    ## 5 102922658306  54.50000   31.17857    1.062500 Neutral
    ## 6  96383783532  53.80000   33.87500    1.070312 Neutral

<img src="Rainband_by_type_files/figure-gfm/unnamed-chunk-8-1.png" style="display: block; margin: auto;" />

## Together By Type

``` r
by_type <- read.csv('full_rainband_by_type.csv')
cat('Number of rows:', nrow(by_type)) # el nino, la nina, and then neutral
```

    ## Number of rows: 4392

``` r
head(by_type)
```

    ##   month day hour     year    width   latmin   latmax   lonmin   lonmax
    ## 1     1   1    0 1996.889 11.34375 25.78125 34.12500 124.6875 136.0312
    ## 2     1   1    6 1996.889 11.06250 25.87500 33.84375 120.5625 131.6250
    ## 3     1   1   12 1996.889 10.08333 25.50000 31.58333 118.0833 128.1667
    ## 4     1   1   18 1996.889 10.58333 24.33333 30.58333 114.9167 125.5000
    ## 5     1   2    0 1996.889  9.25000 22.75000 30.25000 116.8333 126.0833
    ## 6     1   2    6 1996.889 15.00000 21.75000 30.09375 112.4062 127.4062
    ##    rain.amount grid.size averagelat decimaldate    type
    ## 1 115972009378  68.50000   29.95312    1.031250 El Nino
    ## 2 125324063308  78.00000   29.85938    1.039062 El Nino
    ## 3 116888816087  57.33333   28.54167    1.046875 El Nino
    ## 4 132816612491  70.55556   27.45833    1.054688 El Nino
    ## 5 144747219042  69.00000   26.50000    1.062500 El Nino
    ## 6 190903991119 103.12500   25.92188    1.070312 El Nino

<img src="Rainband_by_type_files/figure-gfm/unnamed-chunk-10-1.png" style="display: block; margin: auto;" />
