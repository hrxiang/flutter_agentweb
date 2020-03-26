#import "FlutterAgentwebPlugin.h"
#import <flutter_agentweb/flutter_agentweb-Swift.h>

@implementation FlutterAgentwebPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterAgentwebPlugin registerWithRegistrar:registrar];
}
@end
