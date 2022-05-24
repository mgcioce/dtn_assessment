package org.cioce.coordinate;

public class QuadKey {


    public static String latLonToQuadKey(double lat, double lon, int level) {
        double sinLat = Math.sin(lat * (Math.PI/180.0));
        double twoPowlevel = Math.pow(2.0,(double)level);
        double constTimes2powlevel = 256.0 * twoPowlevel;
        int pixelX = Math.round((float) (((lon + 180.0)/360.0) * constTimes2powlevel));
        int pixely = Math.round((float) ((0.5 - (Math.log(((1.0 + sinLat)/(1.0 - sinLat))))/(4.0 * Math.PI)) * constTimes2powlevel));
        int mapLength = (int) twoPowlevel;
        int tileX = (int) Math.floor((pixelX / 256.0));
        int tileY = (int) Math.floor((pixely / 256.0));
        String quadKeyBase2 = interleaveBits(tileX,tileY,(short) 64);
        long quadKeyBase10 = Long.parseLong(quadKeyBase2,2);
        return Long.toString(quadKeyBase10,4);
    }

    public static String interleaveBits(int firstPositionBits, int secondPositionBits, short numOfBitsToReturn) {
        char[] bitsAsCharArray = new char[numOfBitsToReturn];
        char zero = '0';
        char one = '1';
        boolean firstPositionTurn = true;
        for(int i = numOfBitsToReturn - 1; i >= 0; i--) {
            if(firstPositionTurn) { //firstPositionTurn
                 bitsAsCharArray[i] = firstPositionBits % 2 == 1 ? one : zero;
                 firstPositionBits = firstPositionBits >> 1;
            } else { //secondPositionTurn
                bitsAsCharArray[i] = secondPositionBits % 2 == 1 ? one : zero;
                secondPositionBits = secondPositionBits >> 1;
            }
            firstPositionTurn = !firstPositionTurn;
        }
        return new String(bitsAsCharArray);
    }


}
