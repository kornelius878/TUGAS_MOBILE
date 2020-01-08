package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class LinkProperties implements Parcelable {
  public static final Parcelable.Creator<LinkProperties> CREATOR = new Parcelable.Creator<LinkProperties>() {
      public LinkProperties createFromParcel(Parcel param1Parcel) {
        LinkProperties linkProperties = new LinkProperties();
        String str = param1Parcel.readString();
        if (str != null)
          try {
            linkProperties.setInterfaceName(str);
          } catch (Exception exception) {
            return null;
          }  
        int i = param1Parcel.readInt();
        byte b1 = 0;
        for (byte b2 = 0; b2 < i; b2++)
          linkProperties.addLinkAddress((LinkAddress)param1Parcel.readParcelable(null)); 
        int j = param1Parcel.readInt();
        for (byte b3 = 0; b3 < j; b3++) {
          try {
            linkProperties.addDns(InetAddress.getByAddress(param1Parcel.createByteArray()));
          } catch (UnknownHostException unknownHostException) {}
        } 
        int k = param1Parcel.readInt();
        while (b1 < k) {
          linkProperties.addRoute((RouteInfo)param1Parcel.readParcelable(null));
          b1++;
        } 
        if (param1Parcel.readByte() == 1)
          linkProperties.setHttpProxy((ProxyProperties)param1Parcel.readParcelable(null)); 
        return linkProperties;
      }
      
      public LinkProperties[] newArray(int param1Int) {
        return new LinkProperties[param1Int];
      }
    };
  
  private Collection<InetAddress> mDnses = new ArrayList<InetAddress>();
  
  private ProxyProperties mHttpProxy;
  
  String mIfaceName;
  
  private Collection<LinkAddress> mLinkAddresses = new ArrayList<LinkAddress>();
  
  private Collection<RouteInfo> mRoutes = new ArrayList<RouteInfo>();
  
  public LinkProperties() {
    clear();
  }
  
  public void addDns(InetAddress paramInetAddress) {
    if (paramInetAddress != null)
      this.mDnses.add(paramInetAddress); 
  }
  
  public void addLinkAddress(LinkAddress paramLinkAddress) {
    if (paramLinkAddress != null)
      this.mLinkAddresses.add(paramLinkAddress); 
  }
  
  public void addRoute(RouteInfo paramRouteInfo) {
    if (paramRouteInfo != null)
      this.mRoutes.add(paramRouteInfo); 
  }
  
  public void clear() {
    this.mIfaceName = null;
    this.mLinkAddresses.clear();
    this.mDnses.clear();
    this.mRoutes.clear();
    this.mHttpProxy = null;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof LinkProperties))
      return false; 
    LinkProperties linkProperties = (LinkProperties)paramObject;
    return (isIdenticalInterfaceName(linkProperties) && isIdenticalAddresses(linkProperties) && isIdenticalDnses(linkProperties) && isIdenticalRoutes(linkProperties) && isIdenticalHttpProxy(linkProperties));
  }
  
  public Collection<InetAddress> getAddresses() {
    ArrayList<InetAddress> arrayList = new ArrayList();
    Iterator<LinkAddress> iterator = this.mLinkAddresses.iterator();
    while (iterator.hasNext())
      arrayList.add(((LinkAddress)iterator.next()).getAddress()); 
    return Collections.unmodifiableCollection(arrayList);
  }
  
  public Collection<InetAddress> getDnses() {
    return Collections.unmodifiableCollection(this.mDnses);
  }
  
  public ProxyProperties getHttpProxy() {
    return this.mHttpProxy;
  }
  
  public String getInterfaceName() {
    return this.mIfaceName;
  }
  
  public Collection<RouteInfo> getRoutes() {
    return Collections.unmodifiableCollection(this.mRoutes);
  }
  
  public int hashCode() {
    int j;
    String str = this.mIfaceName;
    if (str == null)
      return 0; 
    int i = str.hashCode() + 31 * this.mLinkAddresses.size() + 37 * this.mDnses.size() + 41 * this.mRoutes.size();
    ProxyProperties proxyProperties = this.mHttpProxy;
    if (proxyProperties == null) {
      j = 0;
    } else {
      j = proxyProperties.hashCode();
    } 
    return j + i;
  }
  
  public boolean isIdenticalAddresses(LinkProperties paramLinkProperties) {
    Collection<InetAddress> collection1 = paramLinkProperties.getAddresses();
    Collection<InetAddress> collection2 = getAddresses();
    return (collection2.size() == collection1.size()) ? collection2.containsAll(collection1) : false;
  }
  
  public boolean isIdenticalDnses(LinkProperties paramLinkProperties) {
    Collection<InetAddress> collection = paramLinkProperties.getDnses();
    return (this.mDnses.size() == collection.size()) ? this.mDnses.containsAll(collection) : false;
  }
  
  public boolean isIdenticalHttpProxy(LinkProperties paramLinkProperties) {
    return (getHttpProxy() == null) ? ((paramLinkProperties.getHttpProxy() == null)) : getHttpProxy().equals(paramLinkProperties.getHttpProxy());
  }
  
  public boolean isIdenticalInterfaceName(LinkProperties paramLinkProperties) {
    return TextUtils.equals(getInterfaceName(), paramLinkProperties.getInterfaceName());
  }
  
  public boolean isIdenticalRoutes(LinkProperties paramLinkProperties) {
    Collection<RouteInfo> collection = paramLinkProperties.getRoutes();
    return (this.mRoutes.size() == collection.size()) ? this.mRoutes.containsAll(collection) : false;
  }
  
  public void setHttpProxy(ProxyProperties paramProxyProperties) {
    this.mHttpProxy = paramProxyProperties;
  }
  
  public void setInterfaceName(String paramString) {
    this.mIfaceName = paramString;
  }
  
  public String toString() {
    String str1;
    String str8;
    if (this.mIfaceName == null) {
      str1 = "";
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("InterfaceName: ");
      stringBuilder.append(this.mIfaceName);
      stringBuilder.append(" ");
      str1 = stringBuilder.toString();
    } 
    String str2 = "LinkAddresses: [";
    for (LinkAddress linkAddress : this.mLinkAddresses) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append(linkAddress.toString());
      stringBuilder.append(",");
      str2 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str2);
    stringBuilder1.append("] ");
    String str3 = stringBuilder1.toString();
    String str4 = "DnsAddresses: [";
    for (InetAddress inetAddress : this.mDnses) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str4);
      stringBuilder.append(inetAddress.getHostAddress());
      stringBuilder.append(",");
      str4 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str4);
    stringBuilder2.append("] ");
    String str5 = stringBuilder2.toString();
    String str6 = "Routes: [";
    for (RouteInfo routeInfo : this.mRoutes) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str6);
      stringBuilder.append(routeInfo.toString());
      stringBuilder.append(",");
      str6 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append(str6);
    stringBuilder3.append("] ");
    String str7 = stringBuilder3.toString();
    if (this.mHttpProxy == null) {
      str8 = "";
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("HttpProxy: ");
      stringBuilder.append(this.mHttpProxy.toString());
      stringBuilder.append(" ");
      str8 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder4 = new StringBuilder();
    stringBuilder4.append(str1);
    stringBuilder4.append(str3);
    stringBuilder4.append(str7);
    stringBuilder4.append(str5);
    stringBuilder4.append(str8);
    return stringBuilder4.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(getInterfaceName());
    paramParcel.writeInt(this.mLinkAddresses.size());
    Iterator<LinkAddress> iterator = this.mLinkAddresses.iterator();
    while (iterator.hasNext())
      paramParcel.writeParcelable(iterator.next(), paramInt); 
    paramParcel.writeInt(this.mDnses.size());
    Iterator<InetAddress> iterator1 = this.mDnses.iterator();
    while (iterator1.hasNext())
      paramParcel.writeByteArray(((InetAddress)iterator1.next()).getAddress()); 
    paramParcel.writeInt(this.mRoutes.size());
    Iterator<RouteInfo> iterator2 = this.mRoutes.iterator();
    while (iterator2.hasNext())
      paramParcel.writeParcelable(iterator2.next(), paramInt); 
    if (this.mHttpProxy != null) {
      paramParcel.writeByte((byte)1);
      paramParcel.writeParcelable(this.mHttpProxy, paramInt);
      return;
    } 
    paramParcel.writeByte((byte)0);
  }
}


/* Location:              E:\TBS_MOBILE\Cawir\dex2jar-0.0.9.15-master\classes-dex2jar.jar!\android\net\LinkProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
