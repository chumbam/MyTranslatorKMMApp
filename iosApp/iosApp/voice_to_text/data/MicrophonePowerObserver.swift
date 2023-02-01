//
//  MicrophonePowerObserver.swift
//  iosApp
//
//  Created by user on 01.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class MicrophonePowerObserver: ObservableObject {
    private var cancellable: AnyCancellable? = nil
    private var audioRecorder: AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    
    private let powerRationEmissionsPerSecond = 20.0
    
    func startObserving() {
        do {
            let recorderSettings: [String: Any] = [
                AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1
            ]
            let recorder = try  AVAudioRecorder(url: URL(fileURLWithPath: "/dev/null", isDirectory: true), settings: recorderSettings)
            recorder.record()
            self.audioRecorder = recorder
            
            self.cancellable = Timer.publish(
                every: 1.0 / powerRationEmissionsPerSecond,
                tolerance: 1.0 / powerRationEmissionsPerSecond,
                on: .main,
                in: .common
            )
            .autoconnect()
            .sink { [weak self] _ in
                recorder.updateMeters()
                
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0.0
                } else {
                    let normalizedOffcet = CGFloat(50 + powerOffset) / 50
                    self?.micPowerRatio = normalizedOffcet
                }
            }
        } catch {
            print("Error occured when observing microphone power: \(error.localizedDescription)")
        }
    }
    
    func release() {
        cancellable = nil
        
        audioRecorder?.stop()
        audioRecorder = nil
        
        micPowerRatio = 0.0
    }
}
